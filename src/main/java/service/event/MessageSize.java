package service.event;

import service.ByteUtils;

public record MessageSize(byte[] size) implements EventPart<Integer> {

    @Override
    public String getName() {
        return "message_size";
    }

    @Override
    public int length() {
        return 4;
    }

    @Override
    public Integer getValue() {
        return ByteUtils.bytesToInt(size);
    }
}
