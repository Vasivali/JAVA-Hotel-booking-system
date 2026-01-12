import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

// Βαλλιανάτος Βασίλειος icsd22010
// Κοκκινάκης Γεώργιος icsd24093

public class Hotel {
    private final String name;
    private final String location;
    private final int stars; 

    private final Map<String, Rentable> rentablesById = new HashMap<>();

    private final Map<Integer, Reservation> reservationsById = new HashMap<>();
    private final Map<String, List<Reservation>> reservationsByCustomer = new HashMap<>();
    private final Map<String, List<Reservation>> reservationsByRentableId = new HashMap<>();

    public Hotel(String name, String location, int stars) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Hotel name required.");
        if (location == null || location.trim().isEmpty()) throw new IllegalArgumentException("Location required.");
        if (stars < 2 || stars > 5) throw new IllegalArgumentException("Stars must be between 2 and 5.");

        this.name = name.trim();
        this.location = location.trim();
        this.stars = stars;
    }

    public String getName() { return name; }
    public String getLocation() { return location; }
    public int getStars() { return stars; }

    public void addRentable(Rentable rentable) {
        if (rentable == null) throw new IllegalArgumentException("Rentable cannot be null.");
        String id = rentable.getId();
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("Rentable id required.");

        if (rentablesById.containsKey(id)) {
            throw new IllegalArgumentException("Rentable with id '" + id + "' already exists.");
        }
        rentablesById.put(id, rentable);
        reservationsByRentableId.put(id, new ArrayList<>());
    }

    public boolean removeRentable(String rentableId) {
        if (rentableId == null) return false;

        List<Reservation> r = reservationsByRentableId.get(rentableId);
        if (r != null && !r.isEmpty()) return false;

        reservationsByRentableId.remove(rentableId);
        return rentablesById.remove(rentableId) != null;
    }

    public Rentable findRentableById(String rentableId) {
        return rentablesById.get(rentableId);
    }

    public List<Rentable> getAllRentables() {
        return new ArrayList<>(rentablesById.values());
    }

    public boolean isAvailable(String rentableId, LocalDate start, LocalDate end) {
        Rentable rentable = rentablesById.get(rentableId);
        if (rentable == null) throw new IllegalArgumentException("No rentable found with id: " + rentableId);
        if (start == null || end == null) throw new IllegalArgumentException("Dates cannot be null.");
        if (!end.isAfter(start)) throw new IllegalArgumentException("End date must be after start date.");

        List<Reservation> existing = reservationsByRentableId.get(rentableId);
        if (existing == null) return true;

        for (Reservation res : existing) {
            if (res.overlaps(start, end)) return false;
        }
        return true;
    }

    public Reservation createReservation(String customerName, LocalDate start, LocalDate end, String rentableId) {
        if (!rentablesById.containsKey(rentableId)) {
            throw new IllegalArgumentException("Unknown rentable id: " + rentableId);
        }
        if (!isAvailable(rentableId, start, end)) {
            throw new IllegalStateException("Rentable is NOT available for this period.");
        }

        Rentable rentable = rentablesById.get(rentableId);
        Reservation reservation = new Reservation(customerName, start, end, rentable);

        reservationsById.put(reservation.getId(), reservation);

        reservationsByCustomer
                .computeIfAbsent(reservation.getCustomerName(), k -> new ArrayList<>())
                .add(reservation);

        reservationsByRentableId.get(rentableId).add(reservation);

        return reservation;
    }

    public Reservation findReservationById(int id) {
        return reservationsById.get(id);
    }

    public List<Reservation> findReservationsByCustomer(String customerName) {
        if (customerName == null) return Collections.emptyList();
        List<Reservation> list = reservationsByCustomer.get(customerName.trim());
        return (list == null) ? Collections.emptyList() : new ArrayList<>(list);
    }

    public List<Reservation> findActiveReservationsOn(LocalDate date) {
        if (date == null) return Collections.emptyList();

        List<Reservation> result = new ArrayList<>();
        for (Reservation r : reservationsById.values()) {
            if (r.isActiveOn(date)) result.add(r);
        }
        return result;
    }

    public boolean deleteReservation(int reservationId) {
        Reservation r = reservationsById.remove(reservationId);
        if (r == null) return false;

        List<Reservation> custList = reservationsByCustomer.get(r.getCustomerName());
        if (custList != null) {
            custList.remove(r);
            if (custList.isEmpty()) reservationsByCustomer.remove(r.getCustomerName());
        }

        List<Reservation> rentList = reservationsByRentableId.get(r.getRentable().getId());
        if (rentList != null) rentList.remove(r);

        return true;
    }

    public Set<String> getOccupiedRentableTypesOn(LocalDate date) {
        Set<String> types = new LinkedHashSet<>();
        for (Reservation r : findActiveReservationsOn(date)) {
            types.add(r.getRentable().getTypeName());
        }
        return types;
    }

    public double getMonthlyRevenue(YearMonth month) {
        if (month == null) return 0.0;

        double sum = 0.0;
        for (Reservation r : reservationsById.values()) {
            YearMonth s = YearMonth.from(r.getStartDate());
            YearMonth e = YearMonth.from(r.getEndDate());
            if (month.equals(s) || month.equals(e)) {
                sum += r.getTotalCost();
            }
        }
        return sum;
    }
    public Roomstats getOccupiedRoomStatsOn(LocalDate date) {
    int common = 0;
    int luxury = 0;

    for (Reservation r : findActiveReservationsOn(date)) {
        Rentable rentable = r.getRentable();
        if (rentable instanceof CommonRoom) {
            common++;
        } else if (rentable instanceof LuxuryRoom) {
            luxury++;
        }
    }
    return new Roomstats(common, luxury);
}

}
