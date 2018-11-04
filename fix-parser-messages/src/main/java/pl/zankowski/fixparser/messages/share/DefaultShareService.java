package pl.zankowski.fixparser.messages.share;

import com.google.inject.Inject;
import pl.zankowski.fixparser.messages.api.share.ShareException;
import pl.zankowski.fixparser.messages.spi.ShareService;

import static pl.zankowski.fixparser.core.framework.CodecUtil.decodeBase64;
import static pl.zankowski.fixparser.core.framework.CodecUtil.encodeBase64;
import static pl.zankowski.fixparser.core.framework.RandomUtil.generateRandomKey;

public class DefaultShareService implements ShareService {

    public static final int CHARACTER_LIMIT = 8192;

    private final ShareRepository shareRepository;

    @Inject
    public DefaultShareService(final ShareRepository shareRepository) {
        this.shareRepository = shareRepository;
    }

    @Override
    public String shareMessage(final String message) throws ShareException {
        if (message.isEmpty()) {
            throw new ShareException("Failed to share message. Message cannot be empty.");
        }
        if (message.length() > CHARACTER_LIMIT) {
            throw new ShareException("Message too long. Message limit is " + CHARACTER_LIMIT + " characters.");
        }
        final String shareKey = generateRandomKey();
        final String shareMessage = encodeBase64(message);
        shareRepository.saveSharedMessage(shareKey, shareMessage);
        return shareKey;
    }

    @Override
    public String getMessage(final String shareKey) {
        return decodeBase64(shareRepository.findMessageByKey(shareKey));
    }

}
