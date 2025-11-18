package main.Air;

public class DesertAir extends Air{
    protected double dustParticles;
    public DesertAir(fileio.AirInput airInput) {
        super(airInput, 65.0);
        this.dustParticles = airInput.getDustParticles();
    }

    @Override
    public double getQuality() {
        double score = (oxygenLevel*2) - (dustParticles*0.2) - (temperature*0.3);
        return round(norm(score));
    }
}
