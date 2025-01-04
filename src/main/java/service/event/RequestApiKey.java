package service.event;

import java.nio.ByteBuffer;

import static service.ByteUtils.wrapWithBytes;

public record RequestApiKey(byte[] key) implements EventPart<Short> {

    public RequestApiKey(short key) {
        this(wrapWithBytes(key));
    }

    @Override
    public String getName() {
        return "request_api_key";
    }

    @Override
    public Short getValue() {
        return ByteBuffer.wrap(key).getShort();
    }
}
