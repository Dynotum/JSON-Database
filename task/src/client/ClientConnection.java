package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

class ClientConnection {

    private final List<String> message;
    private final String type;
    private final int index;
    private static int test = 1;

    public ClientConnection(String type, int index, List<String> message) {
        this.message = message;
        this.type = type;
        this.index = index;


        System.out.println("Client started!");
        clientConnect();
    }

    private void clientConnect() {
        final String address = "127.0.0.1";
        final int port = 55555;
        try (Socket socket = new Socket(InetAddress.getByName(address), port)) {
            final DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            final String responseServer = String.join(" ", type, String.valueOf(index), String.join(" ", message)).trim();

            System.out.println("Sent: " + responseServer);
            output.writeUTF(responseServer);

            final DataInputStream input = new DataInputStream(socket.getInputStream());
            System.out.println("Received: " + input.readUTF());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}