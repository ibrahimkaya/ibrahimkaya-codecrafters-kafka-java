package service.event.response;

import org.apache.log4j.Logger;
import service.event.ErrorCode;
import service.event.Event;
import service.event.MessageSize;
import service.event.RequestApiKey;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class ResponseWriter {
    private static final Logger logger = Logger.getLogger(ResponseWriter.class);

    public void writeOutput(OutputStream outputStream, Event event) throws IOException {

        short version = event.requestApiVersion().getValue();
        if (version < 0 || version > 4) {
            writeWithError(outputStream, event);
        } else {
            writeSuccessfully(outputStream, event);
        }
    }

    private void writeWithError(OutputStream outputStream, Event event) throws IOException {
        short errorCode = 35;
        outputStream.write(new ErrorCode(errorCode).getValueAsByteArray());
    }


    private void writeSuccessfully(OutputStream outputStream, Event event) throws IOException {
        var outputStreamBytes = new ByteArrayOutputStream();
        outputStreamBytes.write(event.correlationId().id());
        outputStreamBytes.write(ErrorCode.notError().code());
        outputStreamBytes.write(ErrorCode.notError().code());
        outputStreamBytes.write(new RequestApiKey((short) 18).getValueAsByteArray());
        outputStreamBytes.write(new RequestApiKey((short) 3).getValueAsByteArray());
        outputStreamBytes.write(new RequestApiKey((short) 4).getValueAsByteArray());
        outputStreamBytes.write(0); //tagged fields ??
        outputStreamBytes.write(new byte[]{0, 0, 0, 0}); // throttle time
        outputStreamBytes.write(0); //tagged fields ??
        int size = outputStreamBytes.size();
        byte[] sizeBytes = new MessageSize(size).getValueAsByteArray();

        logger.info(Arrays.toString(outputStreamBytes.toByteArray()));
        outputStream.write(sizeBytes);
        outputStreamBytes.write(outputStreamBytes.toByteArray());
    }

}
