public class CommonRoom extends Room {
    public static final double BASE_PRICE = 100.0;

    public CommonRoom(String roomId, Bedtype bedtype, boolean hasView) {
        super(roomId, bedtype, hasView);
    }

    
    public String getTypeName() {
        return "Common Room";
    }

    

    public double getDailyCost() {
        double multiplier;

        if (getBedtype() == Bedtype.SINGLE) {
            multiplier = 1.00;
        } else if (getBedtype() == Bedtype.DOUBLE) {
            multiplier = 1.60;
        } else { 
            multiplier = 2.10;
        }

        double price = BASE_PRICE * multiplier;

        if (hasView()) {
            price = price * 1.10;
        }

        return price;
    }
}
