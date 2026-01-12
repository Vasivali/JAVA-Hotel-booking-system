public abstract class Room implements Rentable {
    private final String roomId;
    private final Bedtype bedtype;
    private final boolean hasView;
    


    protected Room(String roomId, Bedtype bedtype, boolean hasView) {
        if (roomId == null || roomId.trim().isEmpty()) {
            throw new IllegalArgumentException("Room id cannot be empty.");
        }
        if (bedtype == null) {
            throw new IllegalArgumentException("Bed type cannot be null.");
        }
        this.roomId = roomId.trim();
        this.bedtype = bedtype;
        this.hasView = hasView;
    }

    
    public String getId() {
        return roomId;
    }

    public Bedtype getBedtype() {
        return bedtype;
    }

    public boolean hasView() {
        return hasView;
    }
}
