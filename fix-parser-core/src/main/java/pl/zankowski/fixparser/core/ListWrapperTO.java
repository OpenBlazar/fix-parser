package pl.zankowski.fixparser.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import java.util.Objects;

import static pl.zankowski.fixparser.core.ListUtil.immutableList;

@JsonPropertyOrder({"list"})
public class ListWrapperTO<T extends ITransferObject> implements ITransferObject {

    private static final long serialVersionUID = -7540846983569212782L;

    private final List<T> list;

    @JsonCreator
    public ListWrapperTO(
            @JsonProperty("list") final List<T> list) {
        this.list = immutableList(list);
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ListWrapperTO<?> that = (ListWrapperTO<?>) o;
        return Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    @Override
    public String toString() {
        return "ListWrapperTO{" +
                "list=" + list +
                '}';
    }

}
