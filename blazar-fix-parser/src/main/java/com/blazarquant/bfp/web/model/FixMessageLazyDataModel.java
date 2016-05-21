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

    public FixMessageLazyDataModel(ParserService parserService, ProviderDescriptor providerDescriptor, UserDetails userDetails) {
        this.parserService = parserService;
        this.userDetails = userDetails;
        this.providerDescriptor = providerDescriptor;
    }

    @Override
    public List<FixMessage> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        return parserService.findMessagesByUser(userDetails, providerDescriptor, first, first + pageSize);
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
