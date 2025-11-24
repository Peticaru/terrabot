package main.Air;


import com.fasterxml.jackson.databind.node.ObjectNode;
import main.GameWorld;
import main.WeatherConditions;

public class DesertAir extends Air{
    protected double dustParticles;
    public DesertAir(fileio.AirInput airInput) {
        super(airInput, 65.0);
        this.dustParticles = airInput.getDustParticles();
    }
    public void toJson(ObjectNode node) {
        node.put("type", this.type);
        node.put("name", this.name);
        node.put("mass", this.mass);
        node.put("humidity", this.humidity);
        node.put("temperature", this.temperature);
        node.put("oxygenLevel", this.oxygenLevel);
        node.put("airQuality", this.getQuality());
        node.put("desertStorm", WeatherConditions.desertStorm);
    }
    @Override
    public double getQuality() {
        double score = (oxygenLevel*2) - (dustParticles*0.2) - (temperature*0.3);
        if (weatherAffected && WeatherConditions.desertStorm) {
            score -= 30;
        }
        return round(norm(score));
    }
}
