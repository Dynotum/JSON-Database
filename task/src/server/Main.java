package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        JSONDatabase database = new JSONDatabase();
//        database.start();
        final ConnectionServer server = new ConnectionServer();
    }
}

class ConnectionServer {

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
//            final String value = input.readUTF();
            System.out.println("Received: Give me a record # 12" /*+ value*/);

            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            final String valueOut = "Sent: A record # 12 was sent!";
            output.writeUTF(valueOut);

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


    public void start() {
        // warn-up
        for (int index = 1; index <= 100; index++) {
            cell.put(index, " ");
        }

        String input = scanner.nextLine();
        while (!input.equalsIgnoreCase("exit")) {

            final String[] stringSplit = input.split(" ");
            final int index = Integer.parseInt(stringSplit[1]);
            if (!isWithinRange(index)) {
                System.out.println(ERROR);
                input = scanner.nextLine();
                continue;
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
                    System.out.println(ERROR);
            }

            input = scanner.nextLine();
        }

    }

    private void deleteByIndex(int index, String[] stringSplit) {
        if (stringSplit.length > 2) {
            System.out.println(ERROR);
            return;
        }

        cell.remove(index);
        System.out.println(OK);

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
        System.out.println(OK);
    }


    private void getKey(int index, String[] inputArray) {
        if (inputArray.length > 2) {
            System.out.println(ERROR);
            return;
        }

        if (!cell.containsKey(index)) {
            System.out.println(ERROR);
        } else if (cell.get(index).equals(" ")) {
            System.out.println(ERROR);
        } else {
            System.out.println(cell.get(index));
        }
    }

    private boolean isWithinRange(int parseInt) {
        return 1 <= parseInt && parseInt <= 100;
    }
}