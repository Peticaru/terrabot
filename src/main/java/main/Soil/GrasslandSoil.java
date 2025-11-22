package main.Soil;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class GrasslandSoil extends Soil {
    private final double rootDensity;

    public GrasslandSoil(fileio.SoilInput soilInput) {
        super(soilInput);
        this.rootDensity = soilInput.getRootDensity();
    }

    @Override
    public void toJson(ObjectNode node) {
        double soilQuality = getQuality();
        node.put("type", type);
        node.put("name", name);
        node.put("mass", mass);
        node.put("nitrogen", nitrogen);
        node.put("waterRetention", waterRetention);
        node.put("soilpH", soilpH);
        node.put("organicMatter", organicMatter);
        node.put("soilQuality", soilQuality);
        node.put("rootDensity", rootDensity);
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
