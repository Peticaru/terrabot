package main;

public abstract class Entity {
    protected String type;
    protected String name;
    protected double mass;
    protected double norm(double score) {
        return Math.max(0, Math.min(100, score));
    }
    protected double round(double score) {
        return Math.round(score * 100.0) / 100.0;
    }
}
