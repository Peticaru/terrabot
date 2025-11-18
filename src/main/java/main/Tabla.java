package main;

class Cell {
    private int x;
    private int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

public class Tabla {
    int width;
    int height;
    Cell[][] grid;

    public Cell getCell (int x, int y) {
        return grid[x][y];
    }
}
