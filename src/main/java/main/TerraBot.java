package main;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class TerraBot {
    private int x, y;
    private int battery;
    private boolean charging;
    private List<Entity> inventory;
    private Map<String, List<String>> knowledgeBase;

    public TerraBot(int battery) {
        this.x = 0;
        this.y = 0;
        this.charging = false;
        this.battery = battery;// Starting battery
        this.inventory = new ArrayList<>();
        this.knowledgeBase = new HashMap<>();
    }

    // Movement, scanning, learning methods...
}