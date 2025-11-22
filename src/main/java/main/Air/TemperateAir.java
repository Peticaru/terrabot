package main.Air;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class TemperateAir extends Air {
    protected double pollenLevel;
    public TemperateAir(fileio.AirInput airInput) {
        super(airInput, 84.0);
        this.pollenLevel = airInput.getPollenLevel();
    }

    public void toJson(ObjectNode node) {
        node.put("type", this.type);
        node.put("name", this.name);
        node.put("mass", this.mass);
        node.put("humidity", this.humidity);
        node.put("temperature", this.temperature);
        node.put("oxygenLevel", this.oxygenLevel);
        node.put("airQuality", this.getQuality());
        node.put("pollenLevel", this.pollenLevel);
    }


    @Override
    public double getQuality() {
        double score = (oxygenLevel*2) + (humidity*0.7) - (pollenLevel*0.1);
        return round(norm(score));
    }

}
