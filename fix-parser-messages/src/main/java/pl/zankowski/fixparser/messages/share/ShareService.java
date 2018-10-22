package pl.zankowski.fixparser.messages.share;

import pl.zankowski.bfp.core.share.exception.ShareException;

public interface ShareService {

    String shareMessage(String message) throws ShareException;

    String getMessageFromKey(String key);

}
