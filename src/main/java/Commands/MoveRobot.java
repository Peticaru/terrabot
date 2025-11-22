package Commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.CommandInput;
import main.Cell;
import main.GameWorld;
import main.TerraBot;

import static java.lang.IO.println;
import static java.util.EnumSet.range;

public class MoveRobot {
    public static void command (CommandInput command,
                                GameWorld gameWorld,
                                TerraBot terrabot,
                                ArrayNode output) {
        int []dl = {1, 0, -1, 0};
        int []dc = {0, 1, 0, -1};

        int X =  terrabot.getX();
        int Y = terrabot.getY();
        double minRisk = Double.MAX_VALUE;
        int bestX = -1;
        int bestY = -1;
        for (int i = 0; i < 4; i++) {
            int newX = X + dc[i];
            int newY = Y + dl[i];
            if (gameWorld.isValidPosition(newX, newY)) {
                Cell nextCell = gameWorld.getCell(newX, newY);
                double score = 0;
                int risks = 2;
                score += nextCell.getSoil().calculateProbability();
                score += nextCell.getAir().getToxicity();
                if (nextCell.getAnimal() != null) {
                    score += nextCell.getAnimal().getAttackRisk();
                    risks++;
                }
                if (nextCell.getPlant() != null) {
                    score += nextCell.getPlant().getStuckRisk();
                    risks++;
                }
                double averageRisk = score / risks;
                averageRisk = Math.round(averageRisk);
                if (averageRisk < minRisk) {
                    minRisk = averageRisk;
                    bestX = newX;
                    bestY = newY;
                }
            }
        }
        println("Best move to (" + bestX + ", " + bestY + ") with risk " + minRisk);
        if (bestX != -1 && bestY != -1) {
            if (terrabot.getBattery() >= minRisk) {
                terrabot.setX(bestX);
                terrabot.setY(bestY);
                terrabot.setBattery(terrabot.getBattery() - minRisk);
                String message = "The robot has successfully moved to position (" + bestX + ", " + bestY + ").";
                JSONOutput.stateSimulation(message, command, output);
            }
            else {
                String message = "ERROR: Not enough battery left. Cannot perform action";
                JSONOutput.stateSimulation(message, command, output);
            }
        }
    }
}
