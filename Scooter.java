public class Scooter extends Vehicle {
    public Scooter(String plateNumber) {
        super(plateNumber);
    }


    public String getTypeName() {
        return "Scooter";
    }

    
    public double getDailyCost() {
        return 20.0;
    }
}

