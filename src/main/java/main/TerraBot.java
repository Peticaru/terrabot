package main;
import java.util.*;

import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class TerraBot {
    private int x, y;
    private double battery;
    private int timeUntilCharged;
    private List<Entity> inventory;
    private LinkedHashMap<String, List<String>> knowledgeBase;

    public TerraBot(int battery) {
        this.x = 0;
        this.y = 0;
        this.timeUntilCharged = -1;
        this.battery = battery;// Starting battery
        this.inventory = new ArrayList<>();
        this.knowledgeBase = new LinkedHashMap<>();
    }

    public boolean findSubject(String name) {
        for (Entity entity : this.inventory) {
            if (entity.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean findFact(String category, String fact) {
        if (!this.knowledgeBase.containsKey(category)) {
            return false;
        }
        return this.knowledgeBase.get(category).contains(fact);
    }

    public void addInventory(Entity entity) {
        this.inventory.add(entity);
    }
    public void addFact(String category, String fact) {
        this.knowledgeBase.putIfAbsent(category, new ArrayList<>());
        this.knowledgeBase.get(category).add(fact);
    }

    public void recharge (int x) {
        this.battery += x;
    }

    // Movement, scanning, learning methods...
}