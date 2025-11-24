package main.Soil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.EqualsAndHashCode;
import main.Entity;

import lombok.Data;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
abstract public class Soil extends Entity {
    protected double nitrogen;
    protected double waterRetention;
    protected double soilpH;
    protected double organicMatter;
    public abstract void toJson(ObjectNode node);

    public Soil (SoilInput soilInput) {
        super();
        this.name = soilInput.getName();
        this.type = soilInput.getType();
        this.mass = soilInput.getMass();
        this.nitrogen = soilInput.getNitrogen();
        this.waterRetention = soilInput.getWaterRetention();
        this.soilpH = soilInput.getSoilpH();
        this.organicMatter = soilInput.getOrganicMatter();
    }
    public void updateWaterRetention(double x) {
        this.waterRetention += x;
        this.waterRetention = round(this.waterRetention);
    }
    public void updateOrganicMatter(double x) {
        this.organicMatter += x;
        this.organicMatter = round(this.organicMatter);
    }
    public abstract double getQuality();
    public abstract double calculateProbability();

    public String getQualityCategory() {
        double quality = getQuality();
        if (quality >= 70) {
            return "good";
        } else if (quality >= 40) {
            return "moderate";
        } else {
            return "poor";
        }
    }
}
