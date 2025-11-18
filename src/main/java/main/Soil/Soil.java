package main.Soil;
import fileio.SoilInput;
import main.Entity;


abstract public class Soil extends Entity {
    protected double nitrogen;
    protected double waterRetention;
    protected double soilpH;
    protected double organicMatter;
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
    }
    public void updateOrganicMatter(double x) {
        this.organicMatter += x;
    }
    public abstract double getQuality();
    abstract double calculateProbability();
}
