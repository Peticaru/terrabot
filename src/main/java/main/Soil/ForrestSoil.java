package main.Soil;
import fileio.SoilInput;

public class ForrestSoil extends Soil {

    private double leafLitter;
    public ForrestSoil(SoilInput soilInput) {
        super(soilInput);
        this.leafLitter = soilInput.getLeafLitter();
    }

    @Override
    public double calcScore() {
        double score = 1.2 * nitrogen + organicMatter * 2 + waterRetention * 1.5 + leafLitter * 0.3;
        score = norm(score);
        score = round(score);
        return score;
    }

    public double calculateProbability() {
        return (waterRetention * 0.6 + leafLitter * 0.4) / 80 * 100;
    }

}
