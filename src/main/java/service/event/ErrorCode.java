package service.event;

import service.ByteUtils;

import java.nio.ByteBuffer;

public record ErrorCode(byte[] code) implements EventPart<Short> {
    public ErrorCode(short code) {
        this(ByteUtils.wrapWithBytes(code, 2));
    }

    @Override
    public String getName() {
        return "error_code";
    }

    @Override
    public int length() {
        return 2;
    }

    @Override
    public Short getValue() {
        return ByteBuffer.wrap(code).getShort();
    }
}