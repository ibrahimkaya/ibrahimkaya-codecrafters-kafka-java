package service.event;

import java.util.Arrays;

public class EventFactory {

    private EventFactory() {
    }

    public static Event createEvent(byte[] buffer) {
        if (buffer == null || buffer.length != 12) {
            throw new IllegalArgumentException("buffer is null or empty");
        }

        MessageSize messageSize = new MessageSize(Arrays.copyOfRange(buffer, 0, 4));
        RequestApiKey requestApiKey = new RequestApiKey(Arrays.copyOfRange(buffer, 4, 6));
        RequestApiVersion requestApiVersion = new RequestApiVersion(Arrays.copyOfRange(buffer, 6, 8));
        CorrelationId correlationId = new CorrelationId(Arrays.copyOfRange(buffer, 8, 12));

        return new Event(messageSize, requestApiKey, requestApiVersion, correlationId);
    }
}
