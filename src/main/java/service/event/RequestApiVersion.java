package service.event;

import java.nio.ByteBuffer;

import static service.ByteUtils.wrapWithBytes;

public record RequestApiVersion(byte[] version) implements EventPart<Short> {

    public RequestApiVersion(short version) {
        this(wrapWithBytes(version));
    }

    @Override
    public String getName() {
        return "request_api_version";
    }

    @Override
    public Short getValue() {
        return ByteBuffer.wrap(version).getShort();
    }
}
