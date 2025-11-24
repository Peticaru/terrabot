package Commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.CommandInput;
import main.Air.Air;
import main.Cell;
import main.GameWorld;
import main.Soil.Soil;
import main.TerraBot;

public class ImproveEnvironment {
    public static void command(CommandInput command,
                               GameWorld gameWorld,
                               TerraBot terrabot,
                               ArrayNode output) {
        final int ENERGY_COST = 10;
        if (terrabot.getBattery() < ENERGY_COST) {
            JSONOutput.stateSimulation("ERROR: Not enough battery left. Cannot perform action", command, output);
            return;
        }
        String name = command.getName();
        String improvementType = command.getImprovementType();
        String fact = getFact(improvementType, name);
        if (!terrabot.findSubject(name)) {
            JSONOutput.stateSimulation("ERROR: Subject not yet saved. Cannot perform action", command, output);
            return;
        }
        if (terrabot.findFact(name, fact)) {
            JSONOutput.stateSimulation("ERROR: Fact not yet saved. Cannot perform action", command, output);
            return;
        }
        terrabot.recharge(-10);
        Cell currentCell = gameWorld.getCell(terrabot.getX(), terrabot.getY());
        Air air = currentCell.getAir();
        Soil soil = currentCell.getSoil();
        String message = applyEffect(improvementType, name, air, soil);

    }
    private static String getFact (String improvementType, String name) {
        return switch (improvementType) {
           case "increaseHumidity" -> "Method to increase humidity";
           case "increaseMoisture" -> "Method to increase moisture";
           case "plantVegetation" -> "Method to plant " + name;
           case "fertilizeSoil" -> "Method to fertilize soil with " + name;
           default -> "";
        };
    }
    private static String applyEffect(String improvementType, String name, Air air, Soil soil) {
        return switch (improvementType) {
            case "increaseHumidity" -> {
                air.updateHumidity(0.2);
                yield "The humidity was successfully increased using " + name;
            }
            case "increaseMoisture" -> {
                soil.updateWaterRetention(0.2);
                yield "The moisture was successfully increased using " + name;
            }
            case "plantVegetation" -> {
                air.updateOxygenLevel(0.3);
                yield "The " + name + " was planted successfully.";
            }
            case "fertilizeSoil" -> {
                soil.updateOrganicMatter(0.3);
                yield "The soil was successfully fertilized using " + name;
            }
            default -> "ERROR: Unknown improvement type.";
        };
    }
}
