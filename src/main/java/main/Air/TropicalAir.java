package main.Air;

public class TropicalAir extends Air {
    protected double co2Level;
    public TropicalAir(fileio.AirInput airInput) {
        super(airInput, 82.0);
        this.co2Level = airInput.getCo2Level();
    }

    @Override
    public double getQuality() {
        double score = (oxygenLevel * 2) + (humidity * 0.5) - (co2Level * 0.01);
        return round(norm(score));
    }
    public boolean getToxicity() {
        double toxicityAQ = 100 * (1 - getQuality() / maxScore);
        double finalResult = round(toxicityAQ);
        return (finalResult > 0.8 * maxScore);
    }
}
