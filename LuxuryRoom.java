public class LuxuryRoom extends Room {
    private final boolean hasjacuzzi;

    public LuxuryRoom(String roomId, boolean hasjacuzzi) {
        super(roomId, Bedtype.DOUBLE, true);
        this.hasjacuzzi = hasjacuzzi;
    }

    public boolean hasjacuzzi() {
        return hasjacuzzi;
    }

    
    public String getTypeName() {
        return "Luxury Room";
    }

    
    public double getDailyCost() {
        double commonDoubleWithView = CommonRoom.BASE_PRICE * 1.60 * 1.10;
        double price = commonDoubleWithView * 1.50;

        if (hasjacuzzi) {
            price = price * 1.10;
        }
        return price;
    }
}
