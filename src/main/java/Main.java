import event.Event;
import event.EventFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Main {
    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        int port = 9092;
        try {
            serverSocket = new ServerSocket(port);
            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);
            // Wait for connection from client.
            clientSocket = serverSocket.accept();
            var outputStream = clientSocket.getOutputStream();
            var inputStream = clientSocket.getInputStream();
            Event readedEvent = readInputStream(inputStream);
            outputStream.write(readedEvent.messageSize().size());
            outputStream.write(readedEvent.correlationId().id());
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

    public static Event readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[12];
        inputStream.read(buffer, 0, 12);
        return EventFactory.createEvent(buffer);
    }
}
