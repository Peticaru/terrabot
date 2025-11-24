package main;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class Entity {
    protected String type;
    protected String name;
    protected double mass;
    protected boolean scanned = false;
    protected int scanTime = 0;
    protected double norm(double score) {
        return Math.max(0, Math.min(100, score));
    }
    protected double round(double score) {
        return Math.round(score * 100.0) / 100.0;
    }

    protected int x, y;
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
