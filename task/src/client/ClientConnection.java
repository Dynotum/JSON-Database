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
    private final String address = "127.0.0.1";
    private final int port = 55555;

    public ClientConnection(String type, int index, List<String> message) {
        this.message = message;
        this.type = type;
        this.index = index;


        System.out.println("Client started!");
        clientConnect();
    }

    private void clientConnect() {
        try (Socket socket = new Socket(InetAddress.getByName(address), port);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            final String responseServer = type.equalsIgnoreCase("exit") ?
                    type : String.join(" ", type, String.valueOf(index), String.join(" ", message)).trim();

            System.out.println("Sent: " + type);
            output.writeUTF(responseServer);

            System.out.println("Received: " + input.readUTF());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}