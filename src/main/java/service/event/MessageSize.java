package service.event;

import service.ByteUtils;

import static service.ByteUtils.wrapWithBytes;

public record MessageSize(byte[] size) implements EventPart<Integer> {

    public MessageSize(int size) {
        this(wrapWithBytes(size));
    }

    @Override
    public String getName() {
        return "message_size";
    }

    @Override
    public Integer getValue() {
        return ByteUtils.bytesToInt(size);
    }
}
