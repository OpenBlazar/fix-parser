package pl.zankowski.fixparser.core.exception;

public class FixParserBusinessException extends Exception {

    private static final long serialVersionUID = 1254663866476850328L;

    public FixParserBusinessException(final String message) {
        super(message);
    }

    public FixParserBusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FixParserBusinessException(final Throwable cause) {
        super(cause);
    }

}
