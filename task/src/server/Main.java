package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        final ConnectionServer server = new ConnectionServer();
    }
}

class ConnectionServer {
    /**
     * the server should serve one client at a time in a loop, and the client should only send one request to the server, get one reply, and exit. After that, the server should wait for another connection.
     */
    public ConnectionServer() {
        System.out.println("Server started!");
        connect();
    }

    private void connect() {
        final String address = "127.0.0.1";
        final int port = 55555;
        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            Socket socket = server.accept();
            DataInputStream input = new DataInputStream(socket.getInputStream());
            final String valueInput = input.readUTF();

            JSONDatabase jsonDatabase = new JSONDatabase(valueInput);

            final String result = jsonDatabase.getResponse();
//            System.out.println("Received: " + result);

            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
//            final String[] value = valueInput.split("#");
//            final String valueOut = "A record # " + value[1].trim() + " was sent!";
//            result =("Sent: " + valueOut);
            output.writeUTF(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

class JSONDatabase {
    final static String ERROR = "ERROR";
    final static String OK = "OK";
    final Scanner scanner = new Scanner(System.in);
    final HashMap<Integer, String> cell = new LinkedHashMap<>();
    private final int MAX_CELLS = 1000;
    private final String valueClient;
    private String result = "";

    public JSONDatabase(String valueClient) {
        this.valueClient = valueClient;
        start();
    }

    public void start() {
        // warn-up
        for (int index = 1; index <= MAX_CELLS; index++) {
            cell.put(index, " ");
        }

//        while (!input.equalsIgnoreCase("exit")) {

        final String[] stringSplit = valueClient.split(" ");
        final int index = Integer.parseInt(stringSplit[1]);
        if (!isWithinRange(index)) {
            result = (ERROR);
//                input = scanner.nextLine();
//                continue;
        }
        switch (stringSplit[0]) {
            case "get":
                getKey(index, stringSplit);
                break;
            case "set":
                setKey(index, stringSplit);
                break;
            case "delete":
                deleteByIndex(index, stringSplit);
                break;
            default:
                result = (ERROR);
        }

//            input = scanner.nextLine();
//        }

    }

    private void deleteByIndex(int index, String[] stringSplit) {
        if (stringSplit.length > 2) {
            result = (ERROR);
            return;
        }

        cell.remove(index);
        result = (OK);

    }

    private void setKey(int index, String[] inputArray) {
        final StringBuilder sb = new StringBuilder();
        if (inputArray.length > 3) { // This means the string contains one or more spaces
            for (int i = 2; i < inputArray.length; i++) {
                sb.append(inputArray[i] + " "); // watchout with last space at the end of the string
            }
        } else {
            sb.append(inputArray[inputArray.length - 1]);
        }

        cell.put(index, sb.toString());
        result = (OK);
    }


    private void getKey(int index, String[] inputArray) {
        if (inputArray.length > 2) {
            result = (ERROR);
            return;
        }

        if (!cell.containsKey(index)) {
            result = (ERROR);
        } else if (cell.get(index).equals(" ")) {
            result = (ERROR);
        } else {
            result = (cell.get(index));
        }
    }

    private boolean isWithinRange(int parseInt) {
        return 1 <= parseInt && parseInt <= MAX_CELLS;
    }

    public String getResponse() {
        return result;
    }
}