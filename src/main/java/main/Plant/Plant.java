package main.Plant;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.EqualsAndHashCode;
import main.Entity;
import fileio.PlantInput;

import main.Soil.Soil;
import main.Water.Water;
import main.Air.Air;


import lombok.Data;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Plant extends Entity {
    public enum Age {
        Young,
        Mature,
        Old,
        Dead
    }
    protected Age age;
    protected static final double []maturity_oxygen_rate = {0.2, 0.7, 0.4, 0.0};
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

    public void updatePlant(Soil soil, Water water, Air air) {
        if (!scanned)
            return;
        grow(0.2);
        if (water != null) {
            grow(0.2);
        }
        if (age != Age.Dead)
            air.updateOxygenLevel(this.getOxygenProduction());
    }

    public void toJson(com.fasterxml.jackson.databind.node.ObjectNode node) {
        node.put("type", this.type);
        node.put("name", this.name);
        node.put("mass", this.mass);
    }

    public double getBlockingProbability() {
        return switch (type) {
            case "FloweringPlants" -> 90.0;
            case "GymnospermsPlants" -> 60.0;
            case "Ferns" -> 30.0;
            case "Mosses" -> 40.0;
            case "Algae" -> 20.0;
            default -> 0.0;
        };
    }

    public double getStuckRisk() {
        return getBlockingProbability() / 100.0;
    }

    private void advanceAge() {
        if (age == Age.Young) {
            age = Age.Mature;
        } else if (age == Age.Mature) {
            age = Age.Old;
        } else if (age == Age.Old) {
            age = Age.Dead;
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
