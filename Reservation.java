import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// Βαλλιανάτος Βασίλειος icsd22010
// Κοκκινάκης Γεώργιος icsd24093

public class Reservation {
    private static int NEXT_ID = 1000;

    private final int id;
    private final String customerName;
    private final LocalDate startDate; 
    private final LocalDate endDate;   
    private final Rentable rentable;

    public Reservation(String customerName, LocalDate startDate, LocalDate endDate, Rentable rentable) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Dates cannot be null.");
        }
        if (!endDate.isAfter(startDate)) {
            throw new IllegalArgumentException("End date must be after start date.");
        }
        if (rentable == null) {
            throw new IllegalArgumentException("Rentable cannot be null.");
        }

        this.id = NEXT_ID++;
        this.customerName = customerName.trim();
        this.startDate = startDate;
        this.endDate = endDate;
        this.rentable = rentable;
    }

    public int getId() { return id; }
    public String getCustomerName() { return customerName; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public Rentable getRentable() { return rentable; }

    public long getNights() {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    public double getTotalCost() {
        return getNights() * rentable.getDailyCost();
    }

    public boolean isActiveOn(LocalDate date) {
        return (date != null) && (!date.isBefore(startDate)) && date.isBefore(endDate);
    }

    public boolean overlaps(LocalDate otherStart, LocalDate otherEnd) {
        return startDate.isBefore(otherEnd) && otherStart.isBefore(endDate);
    }

    
    public String toString() {
        return "Reservation #" + id + " | " + customerName +
               " | " + startDate + " -> " + endDate +
               " | " + rentable.getTypeName() + " (" + rentable.getId() + ")" +
               " | Cost: " + String.format("%.2f", getTotalCost()) + "€";
    }
}
