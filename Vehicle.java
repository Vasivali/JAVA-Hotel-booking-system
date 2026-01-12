public abstract class Vehicle implements Rentable {
    private final String plateNumber;

    protected Vehicle(String plateNumber) {
        if (plateNumber == null || plateNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Plate number cannot be empty.");
        }
        this.plateNumber = plateNumber.trim();
    }

    
    public String getId() {
        return plateNumber;
    }
}

// Βαλλιανάτος Βασίλειος icsd22010
// Κοκκινάκης Γεώργιος icsd24093