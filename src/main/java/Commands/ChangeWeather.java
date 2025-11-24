package Commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.CommandInput;
import main.Cell;
import main.GameWorld;
import main.TerraBot;
import main.WeatherConditions;

import static java.lang.IO.println;
import static java.util.EnumSet.range;

public class ChangeWeather {
    public static void command(CommandInput command,
                               GameWorld gameWorld,
                               TerraBot terrabot,
                               ArrayNode output) {
        String type = command.getType();
        WeatherConditions.type = type;
        switch(type) {
            case "rainfall":
                WeatherConditions.rainfall =  command.getRainfall();
                break;
            case "polarStorm":
                WeatherConditions.windSpeed = command.getWindSpeed();
                break;
            case "newSeason":
                WeatherConditions.Season = command.getSeason();
                break;
            case "desertStorm":
                WeatherConditions.desertStorm = command.isDesertStorm();
                break;
            case "peopleHiking":
                WeatherConditions.numberOfHikers = command.getNumberOfHikers();
                break;
            default:
                println("Unknown weather event type: " + type);
                break;
        }
        String messageRain = "The weather has changed.";
        JSONOutput.stateSimulation(messageRain, command, output);
        gameWorld.applyWeather(true);
    }
}