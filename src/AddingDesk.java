public class AddingDesk {
    private String wplaceID;
    private static int floor;
    private static int room;
    private static int deskID;
    private static String occupied;

    public void setWplaceID(String wplaceID) {
        this.wplaceID = wplaceID;
    }
    public void setFloor(int floor) {
        this.floor = floor;
    }
    public void setRoom(int room) {
        this.room = room;
    }
    public void setDeskID(int deskID) {
        this.deskID = deskID;
    }
    public void setOccupied(String occupied) {
        this.occupied = occupied;
    }
    public String getWplaceID() {
        return wplaceID;
    }
    public static int getFloor() {
        return floor;
    }
    public static int getRoom() {
        return room;
    }
    public static int getDeskID() {
        return deskID;
    }
    public static String getOccupied() {
        return occupied;
    }
}