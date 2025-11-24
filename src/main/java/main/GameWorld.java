package main;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import fileio.CommandInput;
import main.Air.Air;
import main.Plant.Plant;
import main.Water.Water;
import main.Animals.Animal;
import main.Cell;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class GameWorld {
    private Cell[][] grid;
    private int width, height;
    private int currentTime = 0;

    private boolean activeWeather = false;
    private int endTimeWeather = 0;

    public void setCurrentTime(int time) {
        this.currentTime = time;
        if (this.activeWeather && this.endTimeWeather < currentTime) {
            applyWeather(false);
            this.activeWeather = false;
            this.endTimeWeather = 0;
            WeatherConditions.type = null;
        }
    }

    private String getEffectedAirType(String eventType) {
        return switch (eventType) {
            case "rainfall" -> "TropicalAir";
            case "polarStorm" -> "PolarAir";
            case "newSeason" -> "TemperateAir";
            case "desertStorm" -> "DesertAir";
            case "peopleHiking" -> "MountainAir";
            default -> null;
        };
    }

    public void applyWeather(boolean flag) {
        String eventType = WeatherConditions.type;
        if (eventType == null)
            return;
        endTimeWeather = currentTime + 2;
        String airAffected = getEffectedAirType(eventType);
        activeWeather = true;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Air air = grid[i][j].getAir();
                if (air != null && air.getType().equals(airAffected)) {
                    grid[i][j].getAir().setWeatherAffected(flag);
                }
            }
        }
    }

    public GameWorld(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
    }
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
    void setAir (int x, int y, main.Air.Air air) {
        grid[x][y].setAir(air);
    }
    void setWater (int x, int y, main.Water.Water water) {
        grid[x][y].setWater(water);
    }
    void setSoil (int x, int y, main.Soil.Soil soil) {
        grid[x][y].setSoil(soil);
    }
    void setPlant (int x, int y, main.Plant.Plant plant) {
        grid[x][y].setPlant(plant);
    }
    void setAnimal (int x, int y, main.Animals.Animal animal) {
        grid[x][y].setAnimal(animal);
    }
    public Cell getCell(int x, int y) {
        return grid[x][y];
    }
    public void printSimpleGrid() {
        System.out.println("\n=== SIMPLE GRID OVERVIEW ===");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = grid[x][y];
                System.out.print("[" + x + "," + y + ":");

                // Entities
                System.out.print(cell.hasPlant() ? "P" : ".");
                System.out.print(cell.hasAnimal() ? "A" : ".");
                System.out.print(cell.hasWater() ? "W" : ".");
                System.out.print("] ");
            }
            System.out.println();
        }
    }
    private Cell getNextCellForAnimal(int x, int y) {
        int []dl = {1, 0, -1, 0};
        int []dc = {0, 1, 0, -1};

        double bestQuality = 0;
        int bestX = -1;
        int bestY = -1;
        for (int i = 0; i < 4; i++) {
            int newX = x + dc[i];
            int newY = y + dl[i];
            if (isValidPosition(newX, newY)) {
                Cell nextCell = getCell(newX, newY);
                main.Plant.Plant plant = nextCell.getPlant();
                main.Water.Water water = nextCell.getWater();
                if (plant != null && water != null) {
                    if (bestQuality < water.getQuality()) {
                        bestQuality = water.getQuality();
                        bestX = newX;
                        bestY = newY;
                    }
                }
            }
        }
        if (bestX != -1 && bestY != -1) {
            return getCell(bestX, bestY);
        }
        for (int i = 0; i < 4; i++) {
            int newX = x + dc[i];
            int newY = y + dl[i];
            if (isValidPosition(newX, newY)) {
                Cell nextCell = getCell(newX, newY);
                main.Plant.Plant plant = nextCell.getPlant();
                if (plant != null) {
                    return getCell(newX, newY);
                }
            }
        }
        bestQuality = 0;
        bestX = -1;
        bestY = -1;
        for (int i = 0; i < 4; i++) {
            int newX = x + dc[i];
            int newY = y + dl[i];
            if (isValidPosition(newX, newY)) {
                Cell nextCell = getCell(newX, newY);
                main.Water.Water water = nextCell.getWater();
                if (water != null) {
                    if (bestQuality < water.getQuality()) {
                        bestQuality = water.getQuality();
                        bestX = newX;
                        bestY = newY;
                    }
                }
            }
        }
        if (bestX != -1 && bestY != -1) {
            return getCell(bestX, bestY);
        }
        for (int i = 0; i < 4; i++) {
            int newX = x + dc[i];
            int newY = y + dl[i];
            if (isValidPosition(newX, newY)) {
                return getCell(newX, newY);
            }
        }
        return null;
    }
    public void updateScanned() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                main.Cell cell = grid[x][y];
                main.Plant.Plant plant = cell.getPlant();
                main.Water.Water water = cell.getWater();
                main.Soil.Soil soil = cell.getSoil();
                main.Air.Air air = cell.getAir();
                main.Animals.Animal animal = cell.getAnimal();

                if (plant != null && plant.isScanned()) {
                    if (plant.getAge() == main.Plant.Plant.Age.Dead) {
                        cell.setPlant(null);
                        continue;
                    }
                    plant.updatePlant(soil, water, air);
                }
                if (water != null && water.isScanned()) {
                    int time = this.currentTime - water.getScanTime();
                    if (time > 0 && time % 2 == 0)
                        water.updateWater(soil, air);
                }
                if (animal != null && animal.isScanned()) {
                    int time = this.currentTime - animal.getScanTime();
                    animal.updateState(air.isToxic());
                    if (time > 0 && time % 2 == 0) {
                        Cell nextCell = getNextCellForAnimal(x, y);
                        if (animal.isCarnivoreOrParasite() && nextCell.getAnimal() != null) {
                            animal.setMass(animal.getMass() + nextCell.getAnimal().getMass());
                            nextCell.getSoil().updateOrganicMatter(0.5);
                            animal.feed();
                            nextCell.setAnimal(null);
                        }
                        else {
                            animal.updateAnimal(nextCell);
                        }
                        animal.setX(nextCell.getX());
                        animal.setY(nextCell.getY());
                        nextCell.setAnimal(animal);
                        cell.setAnimal(null);
                    }
                    else {
                        animal.updateAnimal(cell);
                    }
                }
            }
        }
    }

}