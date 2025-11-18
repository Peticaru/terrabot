package main.Soil;
import fileio.SoilInput;




abstract public class Soil {
    final private String name;
    final private String type;
    private double mass;
    protected double nitrogen;
    protected double waterRetention;
    protected double soilpH;
    protected double organicMatter;
    public Soil (SoilInput soilInput) {
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
    protected double norm(double score) {
        return Math.max(0, Math.min(100, score));
    }
    protected double round(double score) {
        return Math.round(score * 100.0) / 100.0;
    }
    abstract double calcScore();
    abstract double calculateProbability();
}
