package service.socket;

import org.apache.log4j.Logger;
import service.event.Event;
import service.event.EventFactory;
import service.event.response.ResponseWriter;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author ibrahimkaya
 */
public final class SocketManager {

    private static final Logger logger = Logger.getLogger(SocketManager.class);

    //todo read from config (eg: yaml)
    private static final int PORT = 9092;

    private ServerSocket serverSocket;

    private Socket clientSocket;

    public SocketManager() throws IOException {
        initializeSocket(PORT);
    }

    public SocketManager(int port) throws IOException {
        initializeSocket(port);
    }

    private void initializeSocket(int port) throws IOException {
        try {
            serverSocket = new ServerSocket(port);
            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);
            logger.info("Socket listening on port " + PORT);
        } catch (IOException e) {
            logger.error("Could not initialize socket, possibly port already use, port: {}" + PORT, e);
            throw e;
        }
    }

    public void startWebServer() {
        try {
            ResponseWriter responseWriter = new ResponseWriter();
            // Wait for connection from client.
            clientSocket = serverSocket.accept();
            var outputStream = clientSocket.getOutputStream();
            var inputStream = clientSocket.getInputStream();
            Event readedEvent = readInputStream(inputStream);
            responseWriter.writeOutput(outputStream, readedEvent);
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                logger.error("IOException: " + e.getMessage());
            }
        }
    }

    private Event readInputStream(InputStream inputStream) throws IOException {
        return EventFactory.createEvent(inputStream);
    }

}
