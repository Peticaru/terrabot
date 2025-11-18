package main.Air;

public class TemperateAir extends Air {
    protected double pollenLevel;
    public TemperateAir(fileio.AirInput airInput) {
        super(airInput, 84.0);
        this.pollenLevel = airInput.getPollenLevel();
    }

    @Override
    public double getQuality() {
        double score = (oxygenLevel*2) + (humidity*0.7) - (pollenLevel*0.1);
        return round(norm(score));
    }

}
