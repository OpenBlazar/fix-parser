package com.blazarquant.bfp.fix.parser.definition;

import com.blazarquant.bfp.fix.data.FixField;
import com.blazarquant.bfp.fix.data.FixValue;

/**
 * @author Wojciech Zankowski
 */
public interface FixDefinitionProvider {

    String UNKNOWN = "Unknown";

    FixField getFixField(int tag);

    FixValue getFixValue(int tag, String valueEnum);

}
