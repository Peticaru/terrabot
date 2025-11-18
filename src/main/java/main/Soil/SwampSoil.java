package main.Soil;
import fileio.SoilInput;

public class SwampSoil extends Soil{
    private double waterLogging;
    public SwampSoil(SoilInput soilInput) {
        super(soilInput);
        this.waterLogging = soilInput.getWaterLogging();
    }

    @Override
    public double getQuality() {
        double score = 	(nitrogen * 1.1) + (organicMatter * 2.2) - (waterLogging * 5);
        score = norm(score);
        score = round(score);
        return score;
    }

    public double calculateProbability() {
        return waterLogging * 10;
    }
}
