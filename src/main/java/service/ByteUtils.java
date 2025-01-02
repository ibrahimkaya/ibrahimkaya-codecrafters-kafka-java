package service;

import com.google.common.primitives.Ints;

import static java.nio.ByteBuffer.allocate;

public class ByteUtils {
    private ByteUtils() {
    }

    public static Integer bytesToInt(byte[] bytes) {
        return Ints.fromByteArray(bytes);
    }

    public static <T> byte[] wrapWithBytes(T value, int length) {
        return switch (value) {
            case Short s -> allocate(length).putShort(s).array();
            case Integer i -> allocate(length).putInt(i).array();
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }
}
