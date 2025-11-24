package Commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.CommandInput;
import main.Air.Air;
import main.Cell;
import main.GameWorld;
import main.Soil.Soil;
import main.TerraBot;

import java.util.Objects;

import static java.lang.IO.println;

public class Scan {
    public static void command (CommandInput command,
                                GameWorld gameWorld,
                                TerraBot terrabot,
                                ArrayNode output) {
       if (terrabot.getBattery() < 7) {
           String errorMsg = "ERROR: Not enough battery left. Cannot perform action";
           JSONOutput.stateSimulation(errorMsg, command, output);
           return;
       }
       String color = command.getColor();
       String smell = command.getSmell();
       String sound = command.getSound();
       int X = terrabot.getX();
       int Y = terrabot.getY();
       Cell cell = gameWorld.getCell(X, Y);
       String message = "The scanned object is ";
       String errMsg = "ERROR: Object not found. Cannot perform action";
       if (Objects.equals(color, "none") && Objects.equals(smell, "none") && Objects.equals(sound, "none")) {
            message += "water.";
            if (cell.getWater() == null)
                message = errMsg;
            else {
                cell.getWater().setScanTime(command.getTimestamp());
                cell.getWater().setScanned(true);
                terrabot.addInventory(cell.getWater());
                terrabot.recharge(-7);
            }
        } else if (Objects.equals(sound, "none") && !Objects.equals(smell, "none") && !Objects.equals(color, "none")) {
           message += "a plant.";
           if (cell.getPlant() == null)
               message = errMsg;
           else {
               cell.getPlant().setScanTime(command.getTimestamp());
               cell.getPlant().setScanned(true);
               terrabot.addInventory(cell.getPlant());
               terrabot.recharge(-7);
           }

       } else if (!Objects.equals(sound, "none") && !Objects.equals(smell, "none") && !Objects.equals(color, "none")) {
           message += "an animal.";
           if (cell.getAnimal() == null)
               message = errMsg;
           else {
               cell.getAnimal().setScanTime(command.getTimestamp());
               cell.getAnimal().setScanned(true);
               terrabot.addInventory(cell.getAnimal());
               terrabot.recharge(-7);
           }
       } else {
           message = errMsg;
       }
       JSONOutput.stateSimulation(message, command, output);
    }
}