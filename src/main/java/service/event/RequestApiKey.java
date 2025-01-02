package service.event;

import service.ByteUtils;

public record RequestApiKey(byte[] key) implements EventPart<Integer> {

    @Override
    public String getName() {
        return "request_api_key";
    }

    @Override
    public int length() {
        return 2;
    }

    @Override
    public Integer getValue() {
        return ByteUtils.bytesToInt(key);
    }
}
