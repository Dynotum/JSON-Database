package server;

import java.util.HashMap;
import java.util.LinkedHashMap;

class JSONDatabase {
    private final static String ERROR = "ERROR";
    private final static String OK = "OK";

    private final HashMap<Integer, String> cell = new LinkedHashMap<>();
    private final int MAX_CELLS = 1000;
    private String result = "";
    private boolean isExit = false;

    public JSONDatabase() {
        warnUp();
    }

    public void warnUp() {
        // warn-up
        for (int index = 1; index <= MAX_CELLS; index++) {
            cell.put(index, " ");
        }
    }

    public void doOperation(String valueClient) {
        final String[] stringSplit = valueClient.split(" ");
        final int index = Integer.parseInt(stringSplit[1]);
        if (!isWithinRange(index)) {
            result = (ERROR);
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
            case "exit":
                result = OK;
                isExit = true;
            default:
                result = (ERROR);
        }
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

    public boolean isExit() {
        return isExit;
    }
}