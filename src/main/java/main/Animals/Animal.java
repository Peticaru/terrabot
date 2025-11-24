package main.Animals;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import fileio.AnimalInput;
import lombok.EqualsAndHashCode;
import main.Cell;
import main.Entity;

import java.util.Locale;
import lombok.Data;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Animal extends Entity {
    public enum State {
        HUNGRY,
        FULL,
        SICK
    }
    protected State state;
    public Animal() {
        super();
        this.state = State.FULL;
    }

    public void toJson(com.fasterxml.jackson.databind.node.ObjectNode node) {
        node.put("type", this.type);
        node.put("name", this.name);
        node.put("mass", this.mass);
    }

    private void eatPlant(Cell cell) {
        main.Plant.Plant plant = cell.getPlant();
        if (plant != null && plant.isScanned()) {
            // Simulate eating the plant
            double newMass = round(this.mass + plant.getMass());
            this.mass = newMass;
            cell.setPlant(null);
        }
    }
    private void drinkWater(Cell cell) {
        main.Water.Water water = cell.getWater();
        if (water != null && water.isScanned()) {
            double waterConsumed = calculateWaterConsumption(water.getMass());
            this.mass = round(this.mass + waterConsumed);
            double remainingWaterMass = round(water.getMass() - waterConsumed);
            if (remainingWaterMass <= 0) {
                cell.setWater(null);
            } else {
                water.setMass(remainingWaterMass);
            }
        }
    }

    public void updateAnimal (Cell newCell) {
        if (!scanned)
            return;
        main.Water.Water water = newCell.getWater();
        main.Plant.Plant plant = newCell.getPlant();
        main.Soil.Soil soil = newCell.getSoil();
        boolean fed = false;
        double organicMatter = 0.0;
        if (plant != null && water != null && plant.isScanned() && water.isScanned()) {
            eatPlant(newCell);
            drinkWater(newCell);
            fed = true;
            organicMatter = 0.8;
        }
        else if (plant != null && plant.isScanned()) {
            eatPlant(newCell);
            fed = true;
            organicMatter = 0.5;
        }
        else if (water != null && water.isScanned()) {
            drinkWater(newCell);
            fed = true;
            organicMatter = 0.5;
        }
        if (fed) {
            feed();
        } else {
            makeHungry();
        }
        if (organicMatter > 0 && soil != null)
            soil.updateOrganicMatter(organicMatter);
    }

    public Animal (AnimalInput animalInput) {
        super();
        this.name = animalInput.getName();
        this.type = animalInput.getType();
        this.mass = animalInput.getMass();
        this.state = State.HUNGRY;
    }
    public double getAttackProbability() {
        return switch (type.toUpperCase()) {
            case "HERBIVORES" -> 85; // 1.5
            case "CARNIVORES" -> 30; // 7.0
            case "OMNIVORES" -> 60; // 4.0
            case "DETRITIVORES" -> 90; // 1.0
            case "PARASITES" -> 10; // 9.0
            default -> 0.0;
        };
    }
    public double getAttackRisk() {
        return (100.0 - getAttackProbability()) / 10.0;
    }
    public double calculateWaterConsumption(double availableWaterMass) {
        return Math.min(mass * 0.08, availableWaterMass);
    }
    public double getOrganicMatterProduction() {
        if (state == State.FULL) {
            return 0.8;
        }
        return 0.0;
    }
    public void updateState(boolean isAirToxic) {
        if (isAirToxic) {
            state = State.SICK;
        } else if (state == State.SICK) {
            state = State.HUNGRY;
        }
    }
    public void feed() {
        if (state != State.SICK)
            state = State.FULL;
    }
    public void makeHungry() {
        if (state != State.SICK)
            state = State.HUNGRY;
    }
    public boolean isCarnivoreOrParasite() {
        String typeUpper = type.toUpperCase();
        return typeUpper.equals("CARNIVORES") || typeUpper.equals("PARASITES");
    }
}
