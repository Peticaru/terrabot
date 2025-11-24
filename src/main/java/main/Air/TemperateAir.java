package main.Air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import main.WeatherConditions;

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
        double seasonPenalty = 0.0;
        if (WeatherConditions.Season != null)
            if (WeatherConditions.Season.equalsIgnoreCase("spring"))
                seasonPenalty = 15.0;
        if (weatherAffected) {
            score -= seasonPenalty;
        }
        return round(norm(score));
    }

}
