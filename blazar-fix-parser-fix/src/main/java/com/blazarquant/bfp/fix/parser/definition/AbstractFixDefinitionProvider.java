/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
