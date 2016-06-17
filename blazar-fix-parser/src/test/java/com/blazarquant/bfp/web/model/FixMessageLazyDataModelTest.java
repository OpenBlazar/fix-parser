package com.blazarquant.bfp.web.model;

import com.blazarquant.bfp.common.TestObjectFactory;
import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.parser.definition.DefaultFixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;
import com.blazarquant.bfp.fix.parser.util.FixMessageFactory;
import com.blazarquant.bfp.services.parser.ParserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.primefaces.model.SortOrder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Wojciech Zankowski
 */
public class FixMessageLazyDataModelTest {

    private ParserService parserService;

    @Before
    public void setUp() {
        parserService = mock(ParserService.class);
    }

    @Test
    public void testLoad() {
        final UserID userID = new UserID(0);
        final UserDetails userDetails = TestObjectFactory.createUserDetails(userID, "test");
        final boolean isPermitted = false;
        final ProviderDescriptor providerDescriptor = DefaultFixDefinitionProvider.DESCRIPTOR;

        FixMessageLazyDataModel dataModel = new FixMessageLazyDataModel(
                parserService,
                providerDescriptor,
                userDetails,
                isPermitted
        );
        final List<FixMessage> expectedMessages = Arrays.asList(FixMessageFactory.createFix42Message(0, "0", "HEARTBEAT"));
        when(parserService.findMessagesByUser(eq(userDetails), eq(providerDescriptor), eq(isPermitted), anyInt(), anyInt())).thenReturn(
                expectedMessages
        );

        final int first = 2;
        final int pageSize = 10;
        List<FixMessage> actualMessages = dataModel.load(first, pageSize, "field", SortOrder.ASCENDING, Collections.emptyMap());
        assertEquals(expectedMessages, actualMessages);

        final ArgumentCaptor<UserDetails> userDetailsCaptor = ArgumentCaptor.forClass(UserDetails.class);
        final ArgumentCaptor<ProviderDescriptor> providerDescriptorCaptor = ArgumentCaptor.forClass(ProviderDescriptor.class);
        final ArgumentCaptor<Integer> lowerLimitCaptor = ArgumentCaptor.forClass(Integer.class);
        final ArgumentCaptor<Integer> upperLimitCaptor = ArgumentCaptor.forClass(Integer.class);
        final ArgumentCaptor<Boolean> isPermittedCaptor = ArgumentCaptor.forClass(Boolean.class);
        verify(parserService).findMessagesByUser(
                userDetailsCaptor.capture(),
                providerDescriptorCaptor.capture(),
                isPermittedCaptor.capture(),
                lowerLimitCaptor.capture(),
                upperLimitCaptor.capture()
        );

        assertEquals(userDetails, userDetailsCaptor.getValue());
        assertEquals(providerDescriptor, providerDescriptorCaptor.getValue());
        assertEquals(Integer.valueOf(first), lowerLimitCaptor.getValue());
        assertEquals(Integer.valueOf(first + pageSize), upperLimitCaptor.getValue());
        assertEquals(Boolean.valueOf(isPermitted), isPermittedCaptor.getValue());
    }

    @Test
    public void testGetExistingRowData() {
        final UserID userID = new UserID(0);
        final UserDetails userDetails = TestObjectFactory.createUserDetails(userID, "test");
        final boolean isPermitted = false;
        final ProviderDescriptor providerDescriptor = DefaultFixDefinitionProvider.DESCRIPTOR;

        FixMessageLazyDataModel dataModel = new FixMessageLazyDataModel(
                parserService,
                providerDescriptor,
                userDetails,
                isPermitted
        );

        final List<FixMessage> expectedMessages = Arrays.asList(FixMessageFactory.createFix42Message(0, "0", "HEARTBEAT"));
        dataModel.setWrappedData(expectedMessages);

        FixMessage actualMessage = dataModel.getRowData("0");
        assertEquals(expectedMessages.get(0), actualMessage);
    }

    @Test
    public void testGetNonexistingRowData() {
        final UserID userID = new UserID(0);
        final UserDetails userDetails = TestObjectFactory.createUserDetails(userID, "test");
        final boolean isPermitted = false;
        final ProviderDescriptor providerDescriptor = DefaultFixDefinitionProvider.DESCRIPTOR;

        FixMessageLazyDataModel dataModel = new FixMessageLazyDataModel(
                parserService,
                providerDescriptor,
                userDetails,
                isPermitted
        );

        dataModel.setWrappedData(Collections.emptyList());
        FixMessage actualMessage = dataModel.getRowData("1");
        assertEquals(new FixMessage.Builder().build(), actualMessage);
    }

}
