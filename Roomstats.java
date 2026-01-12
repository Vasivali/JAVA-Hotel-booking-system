public class Roomstats {
    private final int common;
    private final int luxury;

    public Roomstats(int common, int luxury) {
        this.common = common;
        this.luxury = luxury;
    }

    public int getCommon() {
        return common;
    }

    public int getLuxury() {
        return luxury;
    }

    public String toString() {
        return "Common occupied: " + common + ", Luxury occupied: " + luxury;
    }
}

// Βαλλιανάτος Βασίλειος icsd22010
// Κοκκινάκης Γεώργιος icsd24093