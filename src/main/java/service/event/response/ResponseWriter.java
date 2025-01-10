package service.event.response;

import org.apache.log4j.Logger;
import service.event.ErrorCode;
import service.event.Event;
import service.event.RequestApiKey;
import service.event.request.RequestType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import static service.ByteUtils.wrapWithBytes;

public class ResponseWriter {
    private static final Logger logger = Logger.getLogger(ResponseWriter.class);

    public void writeOutput(OutputStream outputStream, Event event) throws IOException {
        if (RequestType.API_VERSIONS.getValue().equals(event.requestApiKey().getValue()) && !isVersionIsInValid(event)) {
            writeSuccessfullyApiVersions(outputStream, event);
        } else {
            writeWithError(outputStream, event);
        }
    }

    private void writeWithError(OutputStream outputStream, Event event) throws IOException {
        logger.warn("Writing error response: ");
        logEvent(event);
        short errorCode = 35;

        int size = 6; //fixed error size
        byte[] sizeBytes = ByteBuffer.allocate(4).putInt(size).array();
        outputStream.write(sizeBytes);
        outputStream.write(event.correlationId().id());
        outputStream.write(new ErrorCode(errorCode).getValueAsByteArray());
    }

    /*
    [tester::#PV1] [Decoder] - .ResponseHeader
    [tester::#PV1] [Decoder]   - .correlation_id (1403436389)
    [tester::#PV1] [Decoder] - .ResponseBody
    [tester::#PV1] [Decoder]   - .error_code (0)
    [tester::#PV1] [Decoder]   - .num_api_keys (1)
    [tester::#PV1] [Decoder]   - .ApiKeys[0]
    [tester::#PV1] [Decoder]     - .api_key (18)
    [tester::#PV1] [Decoder]     - .min_version (3)
    [tester::#PV1] [Decoder]     - .max_version (4)
    [tester::#PV1] [Decoder]     - .TAG_BUFFER
    [tester::#PV1] [Decoder]   - .throttle_time_ms (0)
    [tester::#PV1] [Decoder]   - .TAG_BUFFER
     */
    public static void writeSuccessfullyApiVersions(OutputStream outputStream, Event event) throws IOException {
        var response = new ByteArrayOutputStream();
        logEvent(event);
        response.write(event.correlationId().id());
        response.write(ErrorCode.notError().code());
        response.write(2);                       // array size + 1 ??
        response.write(new RequestApiKey((short) 18).getValueAsByteArray());
        response.write(new RequestApiKey((short) 3).getValueAsByteArray());
        response.write(new RequestApiKey((short) 4).getValueAsByteArray());
        response.write(0);                       // tagged fields
        response.write(wrapWithBytes(0)); // throttle time
        // All requests and responses will end with a tagged field buffer.  If
        // there are no tagged fields, this will only be a single zero byte.
        response.write(0); // tagged fields
        int size = response.size();
        byte[] sizeBytes = wrapWithBytes(size);
        var output = response.toByteArray();
        outputStream.write(sizeBytes);
        outputStream.write(output);
    }

    private boolean isVersionIsInValid(Event event) {
        short version = event.requestApiVersion().getValue();
        return version < 0 || version > 4;
    }

    private static void logEvent(Event event) {
        logger.debug("Api version" + event.requestApiVersion().getValue());
        logger.debug("Api key" + event.requestApiKey().getValue());
        logger.debug("correlationId" + event.correlationId().getValue());
        logger.debug("messageSize" + event.messageSize().getValue());
    }
}
