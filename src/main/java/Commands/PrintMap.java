package Commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.CommandInput;
import main.Air.Air;
import main.Cell;
import main.GameWorld;
import main.Soil.Soil;
import main.TerraBot;

import static java.lang.IO.println;

public class PrintMap {
    public static void command (CommandInput command,
                                GameWorld gameWorld,
                                TerraBot terrabot,
                                ArrayNode output) {
        var root = output.addObject();
        root.put("command", "printMap");

        // The "output" array as in the reference JSON
        var outArray = root.putArray("output");

        int width = gameWorld.getWidth();
        int height = gameWorld.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = gameWorld.getCell(x, y);

                // One entry per section
                var cellNode = outArray.addObject();

                // "section" : [x, y]
                var sectionArray = cellNode.putArray("section");
                sectionArray.add(x);
                sectionArray.add(y);

                // totalNrOfObjects: usually counts plant + animal + water
                int total = 0;
                if (cell.getPlant() != null) total++;
                if (cell.getAnimal() != null) total++;
                if (cell.getWater() != null) total++;

                cellNode.put("totalNrOfObjects", total);

                // soilQuality: "good" | "moderate" | "poor"
                Soil soil = cell.getSoil();
                Air air = cell.getAir();
                cellNode.put("soilQuality", soil.getQualityCategory());
                cellNode.put("airQuality", air.getQualityCategory());
            }
        }
        root.put("timestamp", command.getTimestamp());
    }
}
