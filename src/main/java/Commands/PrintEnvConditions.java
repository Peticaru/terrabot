package Commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import main.Air.Air;
import main.Animals.Animal;
import main.Cell;
import main.GameWorld;
import main.Plant.Plant;
import main.Soil.Soil;
import main.TerraBot;
import main.Water.Water;

public class PrintEnvConditions {
    public static void command (CommandInput command,
                                GameWorld gameWorld,
                                TerraBot terrabot,
                                ArrayNode output) {
        ObjectNode root = output.addObject();
        root.put("command", "printEnvConditions");

        ObjectNode out = root.putObject("output");


        int x = terrabot.getX();
        int y = terrabot.getY();
        Cell cell = gameWorld.getCell(x, y);

        Soil soil = cell.getSoil();
        if (soil != null) {
            ObjectNode soilNode = out.putObject("soil");
            soil.toJson(soilNode);
        }

        // Water
        Water water = cell.getWater();
        if (water != null) {
            ObjectNode waterNode = out.putObject("water");
            water.toJson(waterNode); // same pattern
        }

        // Air
        Air air = cell.getAir();
        if (air != null) {
            ObjectNode airNode = out.putObject("air");
            air.toJson(airNode); // same pattern
        }

        // Plants
        Plant plant = cell.getPlant();
        if (plant != null) {
            ObjectNode plantNode = out.putObject("plants");
            plant.toJson(plantNode);
        }
        // Animals
        Animal animal = cell.getAnimal();
        if (animal != null) {
            ObjectNode animalNode = out.putObject("animals");
            animal.toJson(animalNode);
        }

        root.put("timestamp", command.getTimestamp());
    }
}
