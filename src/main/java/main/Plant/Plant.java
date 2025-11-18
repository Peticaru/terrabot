package main.Plant;

import main.Entity;
import fileio.PlantInput;



public class Plant extends Entity {
    public enum Age {
        Young,
        Mature,
        Old
    }
    protected Age age;
    protected static final double []maturity_oxygen_rate = {0.2, 0.7, 0.4};
    protected double growthRate = 0;
    protected double oxygen_from_plant = 0.0;
    public Plant(PlantInput plantInput) {
        super();
        this.name = plantInput.getName();
        this.type = plantInput.getType();
        this.mass = plantInput.getMass();
        age = Age.Young;
        switch (type) {
            case "FloweringPlants" -> {
                oxygen_from_plant = 6;
            }
            case "Mosses" -> {
                oxygen_from_plant = 0.8;
            }
            case "Algae" -> {
                oxygen_from_plant = 0.5;
            }
            default -> {
                oxygen_from_plant = 0.0;
            }
        }
    }
    private void advanceAge() {
        if (age == Age.Young) {
            age = Age.Mature;
        } else if (age == Age.Mature) {
            age = Age.Old;
        }
    }

    public void grow (double growthAmount) {
        growthRate += growthAmount;
        if (growthRate >= 1.0) {
            advanceAge();
            growthRate = 0.0;
        }
    }
    public double getOxygenProduction() {
        return oxygen_from_plant + maturity_oxygen_rate[age.ordinal()];
    }
}
