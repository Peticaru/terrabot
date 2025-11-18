package main.Air;

public class PolarAir extends Air {
    protected double iceCrystalConcentration;
    public PolarAir(fileio.AirInput airInput) {
        super(airInput, 142.0);
        this.iceCrystalConcentration = airInput.getIceCrystalConcentration();
    }

    @Override
    public double getQuality() {
        double score = (oxygenLevel*2) + (100-Math.abs(temperature)) - (iceCrystalConcentration*0.05);
        return round(norm(score));
    }

}
