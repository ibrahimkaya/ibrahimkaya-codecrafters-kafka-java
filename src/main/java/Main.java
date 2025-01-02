import service.socket.SocketManager;

import java.io.IOException;

/**
 * @author ibrahimkaya
 */
public class Main {
    public static void main(String[] args) throws IOException {
        var socketManager = new SocketManager();
        socketManager.startWebServer();
    }

}
