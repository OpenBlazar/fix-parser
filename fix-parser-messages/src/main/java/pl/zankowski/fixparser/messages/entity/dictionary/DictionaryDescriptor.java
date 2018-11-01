package pl.zankowski.fixparser.messages.entity.dictionary;

import com.google.common.base.MoreObjects;
import pl.zankowski.fixparser.core.entity.IEntity;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryLoaderType;
import pl.zankowski.fixparser.user.api.UserId;

import java.util.Objects;

public class DictionaryDescriptor implements IEntity {

    private static final long serialVersionUID = -1878360863772226497L;

    private final UserId userId;
    private final String providerName;
    private final DictionaryLoaderType loaderType;

    public DictionaryDescriptor(final UserId userId, final String providerName, final DictionaryLoaderType loaderType) {
        this.userId = userId;
        this.providerName = providerName;
        this.loaderType = loaderType;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getProviderName() {
        return providerName;
    }

    public DictionaryLoaderType getLoaderType() {
        return loaderType;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DictionaryDescriptor that = (DictionaryDescriptor) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(providerName, that.providerName) &&
                loaderType == that.loaderType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, providerName, loaderType);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("providerName", providerName)
                .add("loaderType", loaderType)
                .toString();
    }

}
