package main.Air;

public class MountainAir extends Air {
    protected double altitude;
    public MountainAir(fileio.AirInput airInput) {
        super(airInput, 78.0);
        this.altitude = airInput.getAltitude();
    }

    @Override
    public double getQuality() {
        double oxygenFactor = oxygenLevel - (altitude/1000*0.5);
        return round(norm((oxygenFactor*2) + (humidity*0.6)));
    }
}
