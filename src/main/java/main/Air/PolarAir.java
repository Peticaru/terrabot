package main.Air;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class PolarAir extends Air {
    protected double iceCrystalConcentration;
    public PolarAir(fileio.AirInput airInput) {
        super(airInput, 142.0);
        this.iceCrystalConcentration = airInput.getIceCrystalConcentration();
    }

    public void toJson(ObjectNode node) {
        node.put("type", this.type);
        node.put("name", this.name);
        node.put("mass", this.mass);
        node.put("humidity", this.humidity);
        node.put("temperature", this.temperature);
        node.put("oxygenLevel", this.oxygenLevel);
        node.put("airQuality", this.getQuality());
        node.put("iceCrystalConcentration", this.iceCrystalConcentration);
    }


    @Override
    public double getQuality() {
        double score = (oxygenLevel*2) + (100-Math.abs(temperature)) - (iceCrystalConcentration*0.05);
        return round(norm(score));
    }

}
