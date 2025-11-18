package main.Soil;

public class GrasslandSoil extends Soil {
    private double rootDensity;

    public GrasslandSoil(fileio.SoilInput soilInput) {
        super(soilInput);
        this.rootDensity = soilInput.getRootDensity();
    }

    @Override
    public double getQuality() {
        double score = (nitrogen * 1.3) + (organicMatter * 1.5) + (rootDensity * 0.8);
        score = norm(score);
        score = round(score);
        return score;
    }

    public double calculateProbability() {
        return ((50 - rootDensity) + waterRetention * 0.5) / 75 * 100;
    }
}
