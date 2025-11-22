package main.Air;

import com.fasterxml.jackson.databind.node.ObjectNode;

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
        return round(norm((oxygenFactor*2) + (humidity*0.6)));
    }
}
