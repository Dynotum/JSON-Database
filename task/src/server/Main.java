package server;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        JSON json = new JSON();
        json.start();
    }
}

class JSON {
    final static String ERROR = "ERROR";
    final Scanner scanner = new Scanner(System.in);
    final HashMap<Integer, String> cell = new LinkedHashMap<>();


    public void start() {
        // warn-up
        for (int index = 1; index <= 100; index++) {
            cell.put(index, " ");
        }

        cell.put(44, "simong dyno");

        String input = scanner.nextLine();
        while (!input.equalsIgnoreCase("exit")) {

            final String[] stringSplit = input.split(" ");

            switch (stringSplit[0]) {
                case "get":
                    getKey(stringSplit);
                    break;
                case "set":
                    break;
                case "delete":
                    break;
                default:
                    System.out.println(ERROR);
            }

            input = scanner.nextLine();
        }

    }


    private void getKey(String[] inputArray) {
        if (inputArray.length > 2) {
            System.out.println(ERROR);
            return;
        }

        final int index = Integer.parseInt(inputArray[1]);
        if (!isWithinRange(index)) {
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