package service.event;

import service.ByteUtils;

public interface EventPart<T> {

    String getName();

    int length();

    T getValue();

    default byte[] getValueAsByteArray() {
        return ByteUtils.wrapWithBytes(getValue(), length());
    }
}
