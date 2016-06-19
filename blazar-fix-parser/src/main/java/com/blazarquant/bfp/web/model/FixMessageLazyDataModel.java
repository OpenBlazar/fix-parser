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
package com.blazarquant.bfp.web.model;

import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;
import com.blazarquant.bfp.services.parser.ParserService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

/**
 * @author Wojciech Zankowski
 */
public class FixMessageLazyDataModel extends LazyDataModel<FixMessage> {

    private final ParserService parserService;
    private final UserDetails userDetails;
    private final ProviderDescriptor providerDescriptor;
    private final boolean isPermitted;

    public FixMessageLazyDataModel(ParserService parserService, ProviderDescriptor providerDescriptor, UserDetails userDetails, boolean isPermitted) {
        this.parserService = parserService;
        this.userDetails = userDetails;
        this.providerDescriptor = providerDescriptor;
        this.isPermitted = isPermitted;
    }

    @Override
    public List<FixMessage> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        return parserService.findMessagesByUser(userDetails, providerDescriptor, isPermitted, first, first + pageSize);
    }

    @Override
    public FixMessage getRowData(String rowKey) {
        List<FixMessage> messages = (List<FixMessage>) getWrappedData();
        for (FixMessage message : messages) {
            if (String.valueOf(message.getMessageID()).equals(rowKey)) {
                return message;
            }
        }
        return new FixMessage.Builder().build();
    }

}
