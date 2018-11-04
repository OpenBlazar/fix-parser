package pl.zankowski.fixparser.messages.dictionary;

import pl.zankowski.fixparser.messages.api.FixFieldTO;
import pl.zankowski.fixparser.messages.api.FixValueTO;

public interface FixDefinitionProvider {

    String UNKNOWN = "Unknown";

    FixFieldTO getFixField(final int tag);

    FixValueTO getFixValue(final int tag, final String valueEnum);

}
