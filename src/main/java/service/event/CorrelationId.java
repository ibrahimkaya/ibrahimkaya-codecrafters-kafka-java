package service.event;

import service.ByteUtils;

import static service.ByteUtils.wrapWithBytes;

public record CorrelationId(byte[] id) implements EventPart<Integer> {
    public CorrelationId(int id) {
        this(wrapWithBytes(id));
    }
    @Override
    public String getName() {
        return "correlation_id";
    }

    @Override
    public Integer getValue() {
        return ByteUtils.bytesToInt(id);
    }
}
