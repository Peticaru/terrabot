package javaOutput;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;

import java.util.Comparator;
import java.util.List;

public class JSONOutput {
    public static void startSimulationMessage(String message, CommandInput command, ArrayNode output) {
        ObjectNode node = output.addObject();
        node.put("command", command.getType());
        node.put("message", message);
        node.put("timestamp", command.getTimestamp());
    }
    public static void endSimulationMessage(String message, CommandInput command, ArrayNode output) {
        ObjectNode node = output.addObject();
        node.put("command", command.getType());
        node.put("message", message);
        node.put("timestamp", command.getTimestamp());
    }
}
