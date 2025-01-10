package service.event;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

public class EventFactory {
    private static final Logger logger = Logger.getLogger(EventFactory.class);

    private EventFactory() {
    }

    public static Event createEvent(InputStream inputStream) throws IOException {
        MessageSize messageSize = new MessageSize(inputStream.readNBytes(4));
        RequestApiKey requestApiKey = new RequestApiKey(inputStream.readNBytes(2));
        RequestApiVersion requestApiVersion = new RequestApiVersion(inputStream.readNBytes(2));
        CorrelationId correlationId = new CorrelationId(inputStream.readNBytes(4));
        return new Event(messageSize, requestApiKey, requestApiVersion, correlationId);
    }
}
