package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

class ConnectionServer {
    private final String address = "127.0.0.1";
    private final int port = 55555;
    /**
     * the server should serve one client at a time in a loop, and the client should only send one request to the server, get one reply, and exit. After that, the server should wait for another connection.
     */
    public ConnectionServer() {
        System.out.println("Server started!");
    }

    public void connect() {

        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            final JSONDatabase jsonDatabase = new JSONDatabase();
            boolean isExit = false;
            while (!server.isClosed() && !isExit) {
                final Socket clientSocket = server.accept();
                if (clientSocket != null) {
                    final DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                    final String valueInput = input.readUTF();
                    jsonDatabase.doOperation(valueInput);
                    final String result = jsonDatabase.getResponse();
                    final DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
                    output.writeUTF(result);
                    isExit = jsonDatabase.isExit();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}