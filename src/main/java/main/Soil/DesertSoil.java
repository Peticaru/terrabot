package main.Soil;
import fileio.SoilInput;

public class DesertSoil extends Soil{
    private double salinity;
    public DesertSoil(SoilInput soilInput) {
        super(soilInput);
        this.salinity = soilInput.getSalinity();
    }

    @Override
    public double calcScore() {
        double score = (nitrogen * 0.5) + (waterRetention * 0.3) - (salinity * 2);
        score = norm(score);
        score = round(score);
        return score;
    }

    public double calculateProbability() {
        return (100 - waterRetention + salinity) / 100 * 100;
    }
}
