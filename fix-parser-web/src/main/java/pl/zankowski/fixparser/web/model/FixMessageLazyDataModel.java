package pl.zankowski.fixparser.web.model;

//import org.primefaces.model.LazyDataModel;
import pl.zankowski.fixparser.messages.api.FixMessageTO;

public class FixMessageLazyDataModel  {

//    extends LazyDataModel<FixMessageTO>
//    private final MessageService parserService;
//    private final UserDetailsTO userDetails;
//    private final DictionaryDescriptorTO providerDescriptor;
//    private final boolean isPermitted;
//
//    public FixMessageLazyDataModel(MessageService parserService, DictionaryDescriptorTO providerDescriptor, UserDetailsTO userDetails, boolean isPermitted) {
//        this.parserService = parserService;
//        this.userDetails = userDetails;
//        this.providerDescriptor = providerDescriptor;
//        this.isPermitted = isPermitted;
//    }
//
//    @Override
//    public List<FixMessageTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
//        try {
//            return parserService.findMessagesByUser(userDetails.getUserId(), providerDescriptor, isPermitted, first, first + pageSize);
//        } catch (FixParserBusinessException e) {
//            return Lists.newArrayList();
//        }
//    }
//
//    @Override
//    public FixMessageTO getRowData(String rowKey) {
//        List<FixMessageTO> messages = (List<FixMessageTO>) getWrappedData();
//        for (FixMessageTO message : messages) {
//            if (String.valueOf(message.getMessageId()).equals(rowKey)) {
//                return message;
//            }
//        }
//        return new FixMessageTOBuilder().build();
//    }

}
