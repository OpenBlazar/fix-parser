package pl.zankowski.fixparser.messages.spi;

import pl.zankowski.fixparser.messages.api.share.ShareException;

public interface ShareService {

    String shareMessage(String message) throws ShareException;

    String getMessage(String key);

}
