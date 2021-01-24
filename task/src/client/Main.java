package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

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

        final ClientConnection clientConnection = new ClientConnection(main.type, main.index, main.message);
    }
}
