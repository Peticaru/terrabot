package main;

import Commands.PrintEnvConditions;
import Commands.PrintMap;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.CommandInput;

import java.util.List;
import Commands.JSONOutput;

import static java.lang.IO.println;

public class Simulation {
    private static boolean running;
    Simulation() {
        running = false;
    }
    private static void handleCommand (CommandInput command, GameWorld gameWorld, TerraBot terrabot, ArrayNode output) {
        String type = command.getCommand();
        if (command.getTimestamp() < terrabot.getTimeUntilCharged()) {
            String recharegeMsg = "ERROR: Robot still charging. Cannot perform action";
            JSONOutput.stateSimulation(recharegeMsg, command, output);
            return;
        }
        switch (type) {
            case "startSimulation":
                // init flags, maybe reset TerraBot position, etc.
                String message;
                if (running)
                    message = "ERROR: Simulation already started. Cannot perform action";
                else
                    message = "Simulation has started.";
                JSONOutput.stateSimulation(message, command, output);
                running = true;
                break;

            case "endSimulation":
                String message2;
                if (!running)
                    message2 = "ERROR: Simulation not started. Cannot perform action";
                else
                    message2 = "Simulation has ended.";
                JSONOutput.stateSimulation(message2, command, output);
                running = false;
                // maybe dump final state, or just stop processing further commands
                break;

            case "moveRobot":
                if (!running) {
                    String errorMsg = "ERROR: Simulation not started. Cannot perform action";
                    JSONOutput.stateSimulation(errorMsg, command, output);
                    break;
                }

                // call MoveRobot.command(...)
                Commands.MoveRobot.command(command, gameWorld, terrabot, output);
                break;

            case "rechargeBattery":
                // use cmd.getTimeToCharge(), tell terrabot to start charging
                if (!running) {
                    String errorMsg = "ERROR: Simulation not started. Cannot perform action";
                    JSONOutput.stateSimulation(errorMsg, command, output);
                    break;
                }
                println(terrabot.getBattery());
                int timeToCharge = command.getTimeToCharge();
                terrabot.recharge(timeToCharge);
                terrabot.setTimeUntilCharged(command.getTimestamp() + timeToCharge);
                JSONOutput.stateSimulation("Robot battery is charging.", command, output);

                break;

            case "scanObject":
                // let TerraBot scan current cell, store entities or facts
                break;

            case "learnFact":
                // parse cmd.getSubject(), cmd.getComponents(), add fact to robot
                break;

            case "improveEnvConditions":
                // use improvementType, call something on gameWorld + robot
                break;

            case "getEnergyStatus":
                if (!running) {
                    String errorMsg = "ERROR: Simulation not started. Cannot perform action";
                    JSONOutput.stateSimulation(errorMsg, command, output);
                    break;
                }
                String energy = "TerraBot has %d energy points left.";
                energy = String.format(energy, Math.round(terrabot.getBattery()));
                JSONOutput.stateSimulation(energy, command, output);
                break;

            case "printEnvConditions":
                if (!running) {
                    String errorMsg = "ERROR: Simulation not started. Cannot perform action";
                    JSONOutput.stateSimulation(errorMsg, command, output);
                    break;
                }
                // query gameWorld at robot position, build JSON and add to output
                PrintEnvConditions.command(command, gameWorld, terrabot, output);
                break;
            case "printMap":
                if (!running) {
                    String errorMsg = "ERROR: Simulation not started. Cannot perform action";
                    JSONOutput.stateSimulation(errorMsg, command, output);
                    break;
                }
                PrintMap.command(command, gameWorld, terrabot, output);
                // traverse gameWorld map, build JSON representation, add to output
                break;
            default:
                // unknown command type → maybe add error node to output
                break;
        }
    }
    public static void startSimulation(List<CommandInput> command, GameWorld gameWorld, TerraBot terrabot, ArrayNode output) {
        for (CommandInput cmd : command) {
//            gameWorld.updateEnvironment();
//            terrabot.update();
            handleCommand(cmd, gameWorld, terrabot, output);
        }
    }
}
