package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.*;
import main.*;
import main.Air.*;
import main.Water.*;
import main.Soil.*;
import main.Animals.*;
import main.Plant.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.lang.IO.println;


/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {

    private Main() {
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final ObjectWriter WRITER = MAPPER.writer().withDefaultPrettyPrinter();

    /**
     * @param inputPath input file path
     * @param outputPath output file path
     * @throws IOException when files cannot be loaded.
     */
    public static void action(final String inputPath,
                              final String outputPath) throws IOException {

        InputLoader inputLoader = new InputLoader(inputPath);
        ArrayNode output = MAPPER.createArrayNode();

        List<SimulationInput> simulations = inputLoader.getSimulations();
        List<CommandInput> commands = inputLoader.getCommands();

        for (SimulationInput simulation : simulations) {
            // Process each simulation input
            TerritorySectionParamsInput sections = simulation.getTerritorySectionParams();
            System.out.println("Map dimensions: " + simulation.getTerritoryDim()); //5x5

            TerraBot terraBot = new TerraBot(simulation.getEnergyPoints());
            GameWorld gameWorld = getGameWorld(simulation, sections);

            Simulation.startSimulation(commands, gameWorld, terraBot, output);

        }


        /*
         * TODO Implement your function here
         *
         * How to add output to the output array?
         * There are multiple ways to do this, here is one example:
         *
         *
         * ObjectNode objectNode = MAPPER.createObjectNode();
         * objectNode.put("field_name", "field_value");
         *
         * ArrayNode arrayNode = MAPPER.createArrayNode();
         * arrayNode.add(objectNode);
         *
         * output.add(arrayNode);
         * output.add(objectNode);
         *
         */

        File outputFile = new File(outputPath);
        outputFile.getParentFile().mkdirs();
        WRITER.writeValue(outputFile, output);
    }

    private static GameWorld getGameWorld(SimulationInput simulation, TerritorySectionParamsInput sections) {
        int dimX = Integer.parseInt(simulation.getTerritoryDim().split("x")[0]);
        int dimY = Integer.parseInt(simulation.getTerritoryDim().split("x")[1]);
        GameWorld gameWorld = new GameWorld(dimX, dimY);


        if (sections.getAir() != null) {
            for (AirInput airInput : sections.getAir()) {
                for (PairInput coord: airInput.getSections()) {
                    int x = coord.getX();
                    int y = coord.getY();
                    Air air = switch(airInput.getType()) {
                        case "MountainAir" -> new MountainAir(airInput);
                        case "TemperateAir" -> new TemperateAir(airInput);
                        case "TropicalAir" -> new TropicalAir(airInput);
                        case "PolarAir" -> new PolarAir(airInput);
                        case "DesertAir" -> new DesertAir(airInput);
                        default -> null;
                    };
                    gameWorld.setAir(x, y, air);
                }
            }
        }

        if (sections.getSoil() != null) {
            for (SoilInput soilInput : sections.getSoil()) {
                for (PairInput coord: soilInput.getSections()) {
                    int x = coord.getX();
                    int y = coord.getY();
                    Soil soil = switch(soilInput.getType()) {
                        case "DesertSoil" -> new DesertSoil(soilInput);
                        case "ForestSoil" -> new ForrestSoil(soilInput);
                        case "GrasslandSoil" -> new GrasslandSoil(soilInput);
                        case "SwampSoil" -> new SwampSoil(soilInput);
                        case "TundraSoil" -> new TundraSoil(soilInput);
                        default -> null;
                    };
                    gameWorld.setSoil(x, y, soil);
                }
            }
        }

        if (sections.getWater() != null) {
            for (WaterInput waterInput : sections.getWater()) {
                for (PairInput coord: waterInput.getSections()) {
                    int x = coord.getX();
                    int y = coord.getY();
                    Water water = new Water(waterInput);
                    gameWorld.setWater(x, y, water);
                }
            }
        }
        if (sections.getPlants() != null) {
            for (PlantInput plantInput : sections.getPlants()) {
                for (PairInput coord: plantInput.getSections()) {
                    int x = coord.getX();
                    int y = coord.getY();
                    Plant plant = new Plant(plantInput);
                    gameWorld.setPlant(x, y, plant);
                }
            }
        }

        if (sections.getAnimals() != null) {
            for (AnimalInput animalInput : sections.getAnimals()) {
                for (PairInput coord: animalInput.getSections()) {
                    int x = coord.getX();
                    int y = coord.getY();
                    Animal animal = new Animal(animalInput);
                    gameWorld.setAnimal(x, y, animal);
                }
            }
        }
        return gameWorld;
    }
}

