import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        int message_size = 0;
        int correlation_id = 7;
        int port = 9092;
        try {
            serverSocket = new ServerSocket(port);
            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);
            // Wait for connection from client.
            clientSocket = serverSocket.accept();
            var outputStream = clientSocket.getOutputStream();
            outputStream.write(wrapWithBytes(message_size));
            outputStream.write(wrapWithBytes(correlation_id));
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
    }

    public static byte[] wrapWithBytes(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }
}
