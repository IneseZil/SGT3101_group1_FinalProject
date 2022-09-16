public class UserRegistration {
    private String userName;
    private String password;
    private String fullName;
    private String userEmail;
    private String userRole;
    private Integer userID;

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFullName(String fullName){
        this.fullName = fullName;
    }
    public void setUserEmail (String userEmail){
        this.userEmail = userEmail;
    }
    public void setUserRole (String userRole) {
        this.userRole = userRole;
    }
    public void setUserID (int userID) {
        this.userID = userID;
    }
    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    public String getFullName(){
        return fullName;
    }
    public String getUserEmail(){
        return userEmail;
    }
    public String getUserRole(){
        return userRole;
    }
    public int getUserID() {
        return userID;
    }


}
