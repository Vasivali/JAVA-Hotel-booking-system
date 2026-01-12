public class ATV extends Vehicle {
    public ATV(String plateNumber) {
        super(plateNumber);
    }

    public String getTypeName() {
        return "ATV";
    }

    public double getDailyCost() {
        return 30.0;
    }
}

