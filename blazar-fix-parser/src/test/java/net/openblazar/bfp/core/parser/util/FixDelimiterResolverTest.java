package net.openblazar.bfp.core.parser.util;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;

/**
 * @author Wojciech Zankowski
 */
public class FixDelimiterResolverTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    protected FixDelimiterResolver delimiterResolver;

    @Before
    public void setUp() {
        delimiterResolver = new FixDelimiterResolver();
    }

    @Test
    public void testLegalInputTag9Index() {
        assertEquals(9, delimiterResolver.getTag9Index(FixTestConstants.LEGAL_FIX_1));
        assertEquals(9, delimiterResolver.getTag9Index(FixTestConstants.LEGAL_FIX_2));
        assertEquals(63, delimiterResolver.getTag9Index(FixTestConstants.LEGAL_FIX_3));
        assertEquals(9, delimiterResolver.getTag9Index(FixTestConstants.LEGAL_FIX_4));
    }

    @Test
    public void testLegalInputTag9Length() {
        assertEquals(5, delimiterResolver.getTag9Length(FixTestConstants.LEGAL_FIX_1));
        assertEquals(6, delimiterResolver.getTag9Length(FixTestConstants.LEGAL_FIX_2));
        assertEquals(6, delimiterResolver.getTag9Length(FixTestConstants.LEGAL_FIX_3));
        assertEquals(6, delimiterResolver.getTag9Length(FixTestConstants.LEGAL_FIX_4));
    }

    @Test
    public void testLegalInputTag35Index() {
        assertEquals(14, delimiterResolver.getTag35Index(FixTestConstants.LEGAL_FIX_1));
        assertEquals(15, delimiterResolver.getTag35Index(FixTestConstants.LEGAL_FIX_2));
        assertEquals(69, delimiterResolver.getTag35Index(FixTestConstants.LEGAL_FIX_3));
        assertEquals(15, delimiterResolver.getTag35Index(FixTestConstants.LEGAL_FIX_4));
    }

    @Test
    public void testIllegalInputTag9Index_1() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(containsString("Failed to find body length value."));
        delimiterResolver.getTag9Index(FixTestConstants.ILLEGAL_FIX_1);
    }

    @Test
    public void testIllegalInputTag9Index_2() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(containsString("Failed to find body length value."));
        delimiterResolver.getTag9Index(FixTestConstants.ILLEGAL_FIX_2);
    }

    @Test
    public void testIllegalInputTag9Index_3() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(containsString("Failed to find body length value."));
        delimiterResolver.getTag9Index(FixTestConstants.ILLEGAL_FIX_3);
    }

    @Test
    public void testIllegalInputTag9Index_4() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(containsString("Failed to find body length value."));
        delimiterResolver.getTag9Index(FixTestConstants.ILLEGAL_FIX_4);
    }

    @Test
    public void testIllegalInputTag9Length_1() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(containsString("Failed to find body length value."));
        delimiterResolver.getTag9Length(FixTestConstants.ILLEGAL_FIX_1);
    }

    @Test
    public void testIllegalInputTag9Length_2() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(containsString("Failed to find body length value."));
        delimiterResolver.getTag9Length(FixTestConstants.ILLEGAL_FIX_2);
    }

    @Test
    public void testIllegalInputTag9Length_3() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(containsString("Failed to find body length value."));
        delimiterResolver.getTag9Length(FixTestConstants.ILLEGAL_FIX_3);
    }

    @Test
    public void testIllegalInputTag9Length_4() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(containsString("Failed to find body length value."));
        delimiterResolver.getTag9Length(FixTestConstants.ILLEGAL_FIX_4);
    }

    @Test
    public void testIllegalInputTag35Index_1() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(containsString("Failed to find message type value."));
        delimiterResolver.getTag35Index(FixTestConstants.ILLEGAL_FIX_1);
    }

    @Test
    public void testIllegalInputTag35Index_2() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(containsString("Failed to find message type value."));
        delimiterResolver.getTag35Index(FixTestConstants.ILLEGAL_FIX_2);
    }

    @Test
    public void testIllegalInputTag35Index_3() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(containsString("Failed to find message type value."));
        delimiterResolver.getTag35Index(FixTestConstants.ILLEGAL_FIX_3);
    }

    @Test
    public void testIllegalInputTag35Index_4() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(containsString("Failed to find message type value."));
        delimiterResolver.getTag35Index(FixTestConstants.ILLEGAL_FIX_4);
    }

    @Test
    public void testEdgeCase() {
        // Hipotetical edge case, it shouldn't be 8, but 25.
        assertEquals(8, delimiterResolver.getTag9Index(FixTestConstants.EDGE_CASE_FIX));
    }

}
