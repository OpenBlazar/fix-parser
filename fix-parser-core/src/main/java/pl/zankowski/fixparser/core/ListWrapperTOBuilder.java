package pl.zankowski.fixparser.core;

import com.google.common.collect.Lists;

import java.util.List;

public final class ListWrapperTOBuilder<T extends ITransferObject> {

    private List<T> list;

    public ListWrapperTOBuilder() {
        list = Lists.newArrayList();
    }

    public ListWrapperTOBuilder addElement(final T element) {
        this.list.add(element);
        return this;
    }

    public ListWrapperTO<T> build() {
        return new ListWrapperTO<T>(list);
    }

}
