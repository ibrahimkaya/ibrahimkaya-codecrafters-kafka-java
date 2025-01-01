import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * @author ibrahim kaya
 */
public class MainTest {

    @Test
    public void shouldReturnCorrelationIdCorrectFormat() {
        String expectedFormat = "[0, 0, 0, 7]";
        int defaultCorrelationId = 7;

        byte[] result = Main.wrapWithBytes(defaultCorrelationId);
        assertEquals(expectedFormat, Arrays.toString(result));
    }

    @Test
    public void shouldParseCorrectly() {

    }
}