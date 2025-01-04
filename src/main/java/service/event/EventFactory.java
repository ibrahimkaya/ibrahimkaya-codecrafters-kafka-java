package service.event;

import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class EventFactory {
    private static final Logger logger = Logger.getLogger(EventFactory.class);

    private EventFactory() {
    }

    public static Event createEvent(byte[] buffer) {
        if (buffer == null || buffer.length != 12) {
            throw new IllegalArgumentException("buffer is null or empty");
        }

        logger.info("Creating a new event, buffer: " + new String(buffer, StandardCharsets.UTF_8));
        MessageSize messageSize = new MessageSize(Arrays.copyOfRange(buffer, 0, 4));
        RequestApiKey requestApiKey = new RequestApiKey(Arrays.copyOfRange(buffer, 4, 6));
        RequestApiVersion requestApiVersion = new RequestApiVersion(Arrays.copyOfRange(buffer, 6, 8));
        CorrelationId correlationId = new CorrelationId(Arrays.copyOfRange(buffer, 8, 12));

        return new Event(messageSize, requestApiKey, requestApiVersion, correlationId);
    }
}
