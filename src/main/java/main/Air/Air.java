package main.Air;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.EqualsAndHashCode;
import main.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class Air extends Entity {
    protected double humidity;
    protected double temperature;
    protected double oxygenLevel;
    protected final double maxScore;
    protected boolean weatherAffected;
    public Air(AirInput airInput, double maxScore) {
        super();
        this.maxScore = maxScore;
        this.name = airInput.getName();
        this.type = airInput.getType();
        this.mass = airInput.getMass();
        this.humidity = airInput.getHumidity();
        this.temperature = airInput.getTemperature();
        this.oxygenLevel = airInput.getOxygenLevel();
        this.weatherAffected = false;
    }
    public abstract void toJson(ObjectNode node);
    abstract public double getQuality();
    public void updateHumidity(double x) {
        this.humidity += x;
        this.humidity = round(this.humidity);
    }
    public void updateOxygenLevel(double x) {
        this.oxygenLevel += x;
        this.oxygenLevel = round(this.oxygenLevel);
    }
    public double getToxicity () {
        double airQualityScore = getQuality();
        double toxicityAQ = 100 * (1 - airQualityScore / maxScore);
        double finalResult = Math.round(toxicityAQ * 100.0) / 100.0;
        return round(norm(finalResult));
    }
    public boolean isToxic() {
        double airQualityScore = getQuality();
        double toxicityAQ = 100 * (1 - airQualityScore / maxScore);

        return (toxicityAQ > 0.8 * maxScore);
    }
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
