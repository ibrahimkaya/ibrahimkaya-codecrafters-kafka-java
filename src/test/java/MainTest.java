import org.junit.Test;
import service.ByteUtils;
import service.event.ErrorCode;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * @author ibrahimkaya
 */
public class MainTest {

    @Test
    public void shouldReturnCorrelationIdCorrectFormat() {
        String expectedFormat = "[0, 0, 0, 7]";
        int defaultCorrelationId = 7;

        byte[] result = ByteUtils.wrapWithBytes(defaultCorrelationId, 4);
        assertEquals(expectedFormat, Arrays.toString(result));
    }

    @Test
    public void shouldParseCorrectly() {
        short expectedErrorCode = 35;
        var errorCode = new ErrorCode(expectedErrorCode);
        var resultCode = errorCode.getValue();
        assertEquals(expectedErrorCode, resultCode.intValue());
    }

}