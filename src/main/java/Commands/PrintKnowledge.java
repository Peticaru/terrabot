package Commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import main.GameWorld;
import main.TerraBot;

public class PrintKnowledge {
    public static void command(CommandInput command,
                               GameWorld gameWorld,
                               TerraBot terrabot,
                               ArrayNode output) {
        ObjectNode root = output.addObject();
        root.put("command", "printKnowledgeBase");
        ArrayNode knowledgeOutput = root.putArray("output");
        terrabot.getKnowledgeBase().forEach((topic, facts) -> {
            ObjectNode factNode = knowledgeOutput.addObject();
            factNode.put("topic", topic);
            ArrayNode factArray = factNode.putArray("facts");
            facts.forEach(factArray::add);
        });
        root.put("timestamp", command.getTimestamp());
    }
}
