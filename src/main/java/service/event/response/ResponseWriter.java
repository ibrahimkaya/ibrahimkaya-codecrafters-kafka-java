package service.event.response;

import org.apache.log4j.Logger;
import service.ByteUtils;
import service.event.ErrorCode;
import service.event.Event;
import service.event.MessageSize;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseWriter {
    private static final Logger logger = Logger.getLogger(ResponseWriter.class);

    public void writeOutput(OutputStream outputStream, Event event) throws IOException {

        logger.info("consumed event: " + event.toString());

        short version = event.requestApiVersion().getValue();
        boolean versionIsValid = 4 <= version && version <= 18;
        if (versionIsValid) {
            writeSuccessfully(outputStream, event);
        } else {
            writeWithError(outputStream, event);
        }
    }

    private void writeWithError(OutputStream outputStream, Event event) throws IOException {
        int failMessageSize = 4 + 4 + 2;
        outputStream.write(new MessageSize(failMessageSize).getValueAsByteArray());
        outputStream.write(event.correlationId().id());
        short errorCode = 35;
        outputStream.write(new ErrorCode(errorCode).getValueAsByteArray());
    }

    private void writeSuccessfully(OutputStream outputStream, Event event) throws IOException {
        int successfulMessageLength = 4 + 4 + 2 + 2;

        outputStream.write(new MessageSize(successfulMessageLength).getValueAsByteArray());
        outputStream.write(event.correlationId().id());
        outputStream.write(ErrorCode.notError().code());
        short apiVersions = 18;
        outputStream.write(ByteUtils.wrapWithBytes(apiVersions));
    }

}
