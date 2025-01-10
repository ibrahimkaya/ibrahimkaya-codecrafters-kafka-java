import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import service.ByteUtils;
import service.event.*;
import service.event.response.ResponseWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author ibrahimkaya
 */
public class MainTest {

    @Test
    public void shouldReturnCorrelationIdCorrectFormat() {
        String expectedFormat = "[0, 0, 0, 7]";
        int defaultCorrelationId = 7;

        byte[] result = ByteUtils.wrapWithBytes(defaultCorrelationId);
        assertEquals(expectedFormat, Arrays.toString(result));
    }

    @Test
    public void shouldParseCorrectly() {
        short expectedErrorCode = 35;
        var errorCode = new ErrorCode(expectedErrorCode);
        var resultCode = errorCode.getValue();
        assertEquals(expectedErrorCode, resultCode.intValue());
    }

    //@Test
    public void outPutCorrectly() throws IOException {
        // Arrange: Mock the OutputStream
        OutputStream outputStream = mock(OutputStream.class);

        // Use a StringBuilder to capture written content
        StringBuilder capturedOutput = new StringBuilder();

        // Mock behavior for write method
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                byte[] writtenBytes = (byte[]) invocation.getArguments()[0];
                capturedOutput.append(new String(writtenBytes, StandardCharsets.UTF_8)); // Convert bytes to String and append
                return null;
            }
        }).when(outputStream).write(any(byte[].class));

        ResponseWriter responseWriter = new ResponseWriter();
        Event event = new Event(new MessageSize(11), new RequestApiKey((short) 4), new RequestApiVersion((short) 4), new CorrelationId(123));
        responseWriter.writeOutput(outputStream, event);

        var output = capturedOutput.toString();
        System.out.printf(output);
        assertNotNull(output);
    }
}