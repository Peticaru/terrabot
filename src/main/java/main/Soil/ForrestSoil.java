package main.Soil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;

public class ForrestSoil extends Soil {

    private final double leafLitter;
    public ForrestSoil(SoilInput soilInput) {
        super(soilInput);
        this.leafLitter = soilInput.getLeafLitter();
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
        node.put("leafLitter", leafLitter);
    }

    @Override
    public double getQuality() {
        double score = 1.2 * nitrogen + organicMatter * 2 + waterRetention * 1.5 + leafLitter * 0.3;
        score = norm(score);
        score = round(score);
        return score;
    }

    public double calculateProbability() {
        return (waterRetention * 0.6 + leafLitter * 0.4) / 80 * 100;
    }

}
