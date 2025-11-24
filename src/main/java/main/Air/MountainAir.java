package main.Air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import main.WeatherConditions;

public class MountainAir extends Air {
    protected double altitude;
    public MountainAir(fileio.AirInput airInput) {
        super(airInput, 78.0);
        this.altitude = airInput.getAltitude();
    }

    public void toJson(ObjectNode node) {
        node.put("type", this.type);
        node.put("name", this.name);
        node.put("mass", this.mass);
        node.put("humidity", this.humidity);
        node.put("temperature", this.temperature);
        node.put("oxygenLevel", this.oxygenLevel);
        node.put("airQuality", this.getQuality());
        node.put("altitude", this.altitude);
    }

    @Override
    public double getQuality() {
        double oxygenFactor = oxygenLevel - (altitude/1000*0.5);
        double score = (oxygenFactor*2) + (humidity*0.6);
        if (weatherAffected) {
            score -= WeatherConditions.numberOfHikers * 0.1;
        }
        return round(norm(score));
    }
}
