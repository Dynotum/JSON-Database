package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    @Parameter(names = {"--type", "-t"})
    private String type;
    @Parameter(names = {"--index", "-i"})
    private int index;
    @Parameter(names = {"--message", "-m"}, variableArity = true) // If the number of following parameters is unknown
    private List<String> message = new ArrayList<>();

    public static void main(String... args) {
        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build().parse(args);
        System.out.println("t -> " + main.type + " i -> " + main.index + " m -> " + main.message);
//        ClientConnection clientConnection = new ClientConnection();
    }
}

class ClientConnection {

    public ClientConnection() {
        System.out.println("Client started!");
        clientConnect();
    }

    private void clientConnect() {
        final String address = "127.0.0.1";
        final int port = 55555;
        try (Socket socket = new Socket(InetAddress.getByName(address), port)) {
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            final String valueOut = "Give me a record # 12";
            System.out.println("Sent: " + valueOut);
            output.writeUTF(valueOut);

            DataInputStream input = new DataInputStream(socket.getInputStream());
            System.out.println("Received: " + input.readUTF());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
