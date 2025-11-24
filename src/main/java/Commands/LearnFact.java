package Commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.CommandInput;
import main.GameWorld;
import main.TerraBot;
import main.Entity;

import java.util.Objects;

public class LearnFact {
    public static void command (CommandInput command,
                                GameWorld gameWorld,
                                TerraBot terrabot,
                                ArrayNode output) {
        if (terrabot.getBattery() < 2) {
            String errorMsg = "ERROR: Not enough battery left. Cannot perform action";
            JSONOutput.stateSimulation(errorMsg, command, output);
            return;
        }
        for (Entity entity : terrabot.getInventory()) {
            if (Objects.equals(entity.getName(), command.getComponents())) {
                terrabot.addFact(command.getComponents(), command.getSubject());
                terrabot.recharge(-2);
                String message = "The fact has been successfully saved in the database.";
                JSONOutput.stateSimulation(message, command, output);
                return;
            }
        }
        String message = "ERROR: Subject not yet saved. Cannot perform action";
        JSONOutput.stateSimulation(message, command, output);
        return;
    }
}
