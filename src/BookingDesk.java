public class BookingDesk {

    private String wplaceID;
    private String occupied;
    private String dateFrom;
    private String dateTo;
    private int userID;

    public void setWplaceID(String wplaceID) {
        this.wplaceID = wplaceID;
    }
    public void setOccupied(String occupied) {
        this.occupied = occupied;
    }
    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }
    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
    public void setUserID(int userID){
        this.userID = userID;
    }

    public String getWplaceID () {
        return wplaceID;
    }
    public String getOccupied () {
        return occupied;
    }
    public String getDateFrom () {
        return dateFrom;
    }
    public String getDateTo () {
        return dateTo;
    }
    public int getUserID () {
        return userID;
    }

}