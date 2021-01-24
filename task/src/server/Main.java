package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

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

//        JSONDatabase database = new JSONDatabase();
//        database.start();
//        final ConnectionServer server = new ConnectionServer();
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
            final String valueInput = input.readUTF();
            System.out.println("Received: " + valueInput);

            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            final String[] value = valueInput.split("#");
            final String valueOut = "A record # " + value[1].trim() + " was sent!";
            System.out.println("Sent: " + valueOut);
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
    private final int MAX_CELLS = 1000;

    public void start() {
        // warn-up
        for (int index = 1; index <= MAX_CELLS; index++) {
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