package com.blazarquant.bfp.services.parser;

import com.blazarquant.bfp.common.MockitoUtils;
import com.blazarquant.bfp.common.TestObjectFactory;
import com.blazarquant.bfp.core.parser.FixDefinitionProviderManager;
import com.blazarquant.bfp.core.security.util.SecurityUtil;
import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.database.dao.MessageDAO;
import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.parser.definition.DefaultFixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.FixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;
import com.blazarquant.bfp.fix.parser.util.FixMessageFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * @author Wojciech Zankowski
 */
public class ParserServiceImplTest {

    private ParserService parserService;
    private MessageDAO messageDAO;
    private SecurityUtil securityUtil;

    @Before
    public void setUp() throws Exception {
        messageDAO = mock(MessageDAO.class);
        securityUtil = mock(SecurityUtil.class);
        parserService = new ParserServiceImpl(messageDAO, securityUtil);
    }

    @Test
    public void testFindMessagesByUser() {
        final UserID userID = new UserID(9);
        final UserDetails userDetails = TestObjectFactory.createUserDetails(userID, "test");
        final int lowerLimit = 0;
        final int upperLimit = 10;

        List<String> messages = Arrays.asList(
                "8=FIX.4.2\u00019=14\u000135=0\u000110=213\u0001",
                "8=FIX.4.2\u00019=14\u000135=1\u000110=213\u0001"
        );
        List<FixMessage> fixMessages = Arrays.asList(
                FixMessageFactory.createFix42Message(0, "0", "HEARTBEAT"),
                FixMessageFactory.createFix42Message(1, "1", "TESTREQUEST")
        );

        when(securityUtil.decodeMessage(any())).thenAnswer(MockitoUtils.getCallbackAnswer());
        when(messageDAO.findMessageByUserID(userID, lowerLimit, upperLimit)).thenReturn(messages);

        List<FixMessage> actualFixMessages = parserService.findMessagesByUser(
                userDetails,
                DefaultFixDefinitionProvider.DESCRIPTOR,
                true,
                lowerLimit,
                upperLimit);
        assertEquals(fixMessages, actualFixMessages);
    }

    @Test
    public void testCountUserMessages() {
        final UserID userID = new UserID(0);
        final UserDetails userDetails = TestObjectFactory.createUserDetails(userID, "test");
        final int messages = 14;
        when(messageDAO.countUserMessages(userID)).thenReturn(messages);

        int actualMessages = parserService.countUserMessages(userDetails);
        assertEquals(messages, actualMessages);
    }

    @Test
    public void testParseInput() {
        final String input = "8=FIX.4.2#9=14#35=0#10=213#";
        List<FixMessage> messageList = parserService.parseInput(input);
        assertEquals(1, messageList.size());

        FixMessage fixMessage = FixMessageFactory.createFix42Message(0, "0", "HEARTBEAT");
        assertEquals(fixMessage, messageList.get(0));
    }

    @Test
    public void testParseInputWithProvider() {
        final String input = "8=FIX.4.2#9=14#35=0#10=213#";

        List<FixMessage> messageList = parserService.parseInput(
                DefaultFixDefinitionProvider.DESCRIPTOR,
                new UserID(0),
                input,
                true
        );
        FixMessage fixMessage = FixMessageFactory.createFix42Message(0, "0", "HEARTBEAT");
        assertEquals(fixMessage, messageList.get(0));
    }

    @Test
    public void testSaveMessages() {
        final UserID userID = new UserID(0);
        final UserDetails userDetails = TestObjectFactory.createUserDetails(userID, "test");
        List<FixMessage> fixMessages = Arrays.asList(
                FixMessageFactory.createFix42Message(0, "0", "HEARTBEAT"),
                FixMessageFactory.createFix42Message(1, "1", "TESTREQUEST")
        );

        final ArgumentCaptor<UserID> userIDCaptor = ArgumentCaptor.forClass(UserID.class);
        final ArgumentCaptor<FixMessage> messagesCaptor = ArgumentCaptor.forClass(FixMessage.class);

        parserService.saveMessages(userDetails, fixMessages);
        verify(messageDAO, times(2)).saveMessage(userIDCaptor.capture(), messagesCaptor.capture());
        List<UserID> capturedIDs = userIDCaptor.getAllValues();
        List<FixMessage> capturedMessages = messagesCaptor.getAllValues();

        assertEquals(userID, capturedIDs.get(0));
        assertEquals(fixMessages.get(0), capturedMessages.get(0));
        assertEquals(userID, capturedIDs.get(1));
        assertEquals(fixMessages.get(1), capturedMessages.get(1));
    }

    @Test
    public void testClearHistory() {
        final UserID userID = new UserID(9);
        messageDAO.clearHistory(userID);

        final ArgumentCaptor<UserID> userIDCaptor = ArgumentCaptor.forClass(UserID.class);
        verify(messageDAO, times(1)).clearHistory(userIDCaptor.capture());
        assertEquals(userID, userIDCaptor.getValue());
    }

    @Test
    public void testGetDefinitionProvider() {
        FixDefinitionProvider definitionProvider = parserService.getDefinitionProvider(
                DefaultFixDefinitionProvider.DESCRIPTOR, new UserID(9), true);
        assertTrue(definitionProvider instanceof DefaultFixDefinitionProvider);

        definitionProvider = parserService.getDefinitionProvider(
                DefaultFixDefinitionProvider.DESCRIPTOR, new UserID(9), false);
        assertTrue(definitionProvider instanceof DefaultFixDefinitionProvider);
    }

    /**
     * There was a NullPointerException when we tried to get DefinitionProvider
     * with null ProviderDescriptor and permission set to true.
     */
    @Test
    public void testGetDefinitionProviderWithNullProvider() {
        FixDefinitionProvider definitionProvider = parserService.getDefinitionProvider(null, new UserID(5), true);
        assertTrue(definitionProvider instanceof DefaultFixDefinitionProvider);
    }

    @Test
    public void testGetProviders() {
        Set<ProviderDescriptor> expectedDescriptors = new HashSet<>();
        expectedDescriptors.add(DefaultFixDefinitionProvider.DESCRIPTOR);

        Set<ProviderDescriptor> providerDescriptorSet = parserService.getProviders(new UserID(9), true);
        assertEquals(1, providerDescriptorSet.size());
        assertEquals(expectedDescriptors, providerDescriptorSet);

        providerDescriptorSet = parserService.getProviders(new UserID(0), false);
        assertEquals(1, providerDescriptorSet.size());
        assertEquals(expectedDescriptors, providerDescriptorSet);
    }

    @Test
    public void testIsProProvider() {
        assertFalse(parserService.isProProvider(DefaultFixDefinitionProvider.DESCRIPTOR));
    }

}