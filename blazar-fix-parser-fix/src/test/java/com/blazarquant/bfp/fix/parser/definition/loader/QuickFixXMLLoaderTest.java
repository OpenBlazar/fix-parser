package com.blazarquant.bfp.fix.parser.definition.loader;

import com.blazarquant.bfp.fix.data.FixField;
import com.blazarquant.bfp.fix.data.FixValue;
import com.blazarquant.bfp.fix.parser.definition.CustomFixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.FixDefinitionProvider;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Wojciech Zankowski
 */
public class QuickFixXMLLoaderTest {

    private FixDefinitionProvider definitionProvider;

    @Before
    public void setUp() throws Exception {
        QuickFixXMLLoader fixXMLLoader = new QuickFixXMLLoader();
        definitionProvider = new CustomFixDefinitionProvider(
                fixXMLLoader.parseDocument(getClass().getClassLoader().getResourceAsStream("CUSTOM.xml")));
    }

    @Test
    public void testXMLLoader() {
        FixField advSideField = definitionProvider.getFixField(4);
        assertEquals(4, advSideField.getTag());
        assertEquals("AdvSide", advSideField.getName());

        FixValue advSideSellValue = definitionProvider.getFixValue(4, "S");
        assertEquals("S", advSideSellValue.getValue());
        assertEquals("SELL", advSideSellValue.getDescription());

        FixValue advSideBuyValue = definitionProvider.getFixValue(4, "B");
        assertEquals("B", advSideBuyValue.getValue());
        assertEquals("BUY", advSideBuyValue.getDescription());

        FixField advTransType = definitionProvider.getFixField(5);
        assertEquals(5, advTransType.getTag());
        assertEquals("AdvTransType", advTransType.getName());

        FixValue advTransTypeNew = definitionProvider.getFixValue(5, "N");
        assertEquals("N", advTransTypeNew.getValue());
        assertEquals("NEW", advTransTypeNew.getDescription());

        FixField advTransType2 = definitionProvider.getFixField(6);
        assertEquals(6, advTransType2.getTag());
        assertEquals("AdvTransType2", advTransType2.getName());

        FixValue advTransType2Cancel = definitionProvider.getFixValue(6, "C");
        assertEquals("C", advTransType2Cancel.getValue());
        assertEquals("CANCEL", advTransType2Cancel.getDescription());
    }


}
