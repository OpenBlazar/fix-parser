package pl.zankowski.fixparser.messages.api.dictionary;

import pl.zankowski.fixparser.core.exception.FixParserBusinessException;

public class DefinitionProviderNotFoundException extends FixParserBusinessException {

    public DefinitionProviderNotFoundException() {
    }

    public DefinitionProviderNotFoundException(final String message) {
        super(message);
    }

    public DefinitionProviderNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DefinitionProviderNotFoundException(final Throwable cause) {
        super(cause);
    }

}
