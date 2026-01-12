public class Car extends Vehicle {
    public Car(String plateNumber) {
        super(plateNumber);
    }

    
    public String getTypeName() {
        return "Car";
    }

   
    public double getDailyCost() {
        return 50.0;
    }
}



