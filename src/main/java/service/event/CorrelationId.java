package service.event;

import service.ByteUtils;

public record CorrelationId(byte[] id) implements EventPart<Integer> {

    @Override
    public String getName() {
        return "correlation_id";
    }

    @Override
    public int length() {
        return 4;
    }

    @Override
    public Integer getValue() {
        return ByteUtils.bytesToInt(id);
    }
}
