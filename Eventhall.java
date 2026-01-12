public class Eventhall implements Rentable {
    private final String name;

    public Eventhall(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Event hall name cannot be empty.");
        }
        this.name = name.trim();
    }

    
    public String getId() {
        return name;
    }

    
    public String getTypeName() {
        return "Event Hall";
    }

    
    public double getDailyCost() {
        return 400.0;
    }
}

// Βαλλιανάτος Βασίλειος icsd22010
// Κοκκινάκης Γεώργιος icsd24093