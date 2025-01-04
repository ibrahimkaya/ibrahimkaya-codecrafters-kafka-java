package service.event;

import static service.ByteUtils.wrapWithBytes;

public interface EventPart<T> {

    String getName();

    T getValue();

    default byte[] getValueAsByteArray() {
        return wrapWithBytes(getValue());
    }
}
