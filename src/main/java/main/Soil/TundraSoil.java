package main.Soil;
import fileio.SoilInput;

public class TundraSoil extends Soil{
    private double permafrostDepth;
    public TundraSoil(SoilInput soilInput) {
        super(soilInput);
        this.permafrostDepth = soilInput.getPermafrostDepth();
    }

    @Override
    public double getQuality() {
        double score = (nitrogen * 0.7) + (organicMatter * 0.5) - (permafrostDepth * 1.5);
        score = norm(score);
        score = round(score);
        return score;
    }

    public double calculateProbability() {
        return 	(50 - permafrostDepth) / 50 * 100;
    }
}
