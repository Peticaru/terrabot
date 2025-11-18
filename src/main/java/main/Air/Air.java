package main.Air;
import fileio.AirInput;
import main.Entity;

abstract class Air extends Entity {
    protected double humidity;
    protected double temperature;
    protected double oxygenLevel;
    protected final double maxScore;
    public Air(AirInput airInput, double max_score) {
        super();
        this.maxScore = max_score;
        this.name = airInput.getName();
        this.type = airInput.getType();
        this.mass = airInput.getMass();
        this.humidity = airInput.getHumidity();
        this.temperature = airInput.getTemperature();
        this.oxygenLevel = airInput.getOxygenLevel();
    }
    abstract public double getQuality();
    void updateHumidity(double x) {
        this.humidity += x;
    }
    public boolean getToxicity() {
        double toxicityAQ = 100 * (1 - getQuality() / maxScore);
        return (toxicityAQ > 0.8 * maxScore);
    }
}
