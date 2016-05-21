package com.blazarquant.bfp.fix.parser.definition;

import com.blazarquant.bfp.fix.data.definition.FixDictionary;

import java.util.Map;

/**
 * @author Wojciech Zankowski
 */
public class CustomFixDefinitionProvider extends AbstractFixDefinitionProvider {

    public CustomFixDefinitionProvider(Map<Integer, FixDictionary> dictionaryMap) {
        super(dictionaryMap);
    }

}
