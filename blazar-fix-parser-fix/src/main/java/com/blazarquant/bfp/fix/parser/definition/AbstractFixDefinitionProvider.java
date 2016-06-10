package com.blazarquant.bfp.fix.parser.definition;

import com.blazarquant.bfp.fix.data.FixField;
import com.blazarquant.bfp.fix.data.FixValue;
import com.blazarquant.bfp.fix.data.definition.FixDictionary;

import java.util.Map;

/**
 * @author Wojciech Zankowski
 */
public abstract class AbstractFixDefinitionProvider implements FixDefinitionProvider {

    private final Map<Integer, FixDictionary> dictionaryMap;

    public AbstractFixDefinitionProvider(Map<Integer, FixDictionary> dictionaryMap) {
        this.dictionaryMap = dictionaryMap;
    }

    @Override
    public FixField getFixField(int tag) {
        FixDictionary fixDictionary = dictionaryMap.get(tag);
        if (fixDictionary == null) {
            return new FixField(tag, UNKNOWN);
        }
        return new FixField(fixDictionary.getTag(), fixDictionary.getName());
    }

    @Override
    public FixValue getFixValue(int tag, String valueEnum) {
        FixDictionary fixDictionary = dictionaryMap.get(tag);
        if (fixDictionary == null) {
            return new FixValue(valueEnum, "");
        }
        String valueDescription = fixDictionary.getValues().get(valueEnum);
        if (valueDescription == null) {
            return new FixValue(valueEnum, "");
        }
        return new FixValue(valueEnum, valueDescription);
    }

}
