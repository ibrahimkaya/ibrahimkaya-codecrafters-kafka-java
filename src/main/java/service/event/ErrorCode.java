package service.event;

import service.ByteUtils;

import java.nio.ByteBuffer;

public record ErrorCode(byte[] code) implements EventPart<Short> {
    public ErrorCode(short code) {
        this(ByteUtils.wrapWithBytes(code));
    }

    @Override
    public String getName() {
        return "error_code";
    }

    @Override
    public Short getValue() {
        return ByteBuffer.wrap(code).getShort();
    }

    public static ErrorCode notError() {
        return new ErrorCode((short) 0);
    }
}