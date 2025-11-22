package main.Animals;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import fileio.AnimalInput;
import lombok.EqualsAndHashCode;
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
