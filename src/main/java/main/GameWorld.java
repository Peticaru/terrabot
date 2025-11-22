package main;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import main.Cell;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class GameWorld {
    private Cell[][] grid;
    private int width, height;

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

}