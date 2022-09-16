import java.sql.*;

public class DBConnection {

    String dbURL = "jdbc:mysql://localhost:3306/java31_group1";
    String dbUser = "root";
    String dbPass = "1234";


    public int createUser(String userName, String password, String fullName, String userEmail, String userRole){
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass)) {

            String sql = "INSERT INTO users (userName, password, fullName, userEmail, userRole) VALUES (?,?,?,?,?);";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, fullName);
            preparedStatement.setString(4, userEmail);
            preparedStatement.setString(5, userRole);
            preparedStatement.executeUpdate();

            String sqlID = "SELECT * FROM users WHERE userName ='" + userName + "' and password ='" + password+ "'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlID);


            if (resultSet.next()) {
                return resultSet.getInt(1);//returns current users ID Nr.
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  0;
    }
    public int checkUser(String userName){

        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass)) {
            String sqlUser = "SELECT * FROM users WHERE userName ='" + userName + "'";

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlUser);

            if (resultSet.next()) {

                //returns Users ID Nr.
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int checkLogin(String userName, String password) {

        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass)) {
            String sql = "SELECT * FROM users WHERE userName ='" + userName + "' and password ='" + password+ "'";

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                //returns current Users ID Nr.
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int readListDesk() {
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass)) {
            String listDesk = "SELECT * FROM workplaces WHERE occupied ='Y'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(listDesk);

            while (resultSet.next()) {
                String wplaceID = resultSet.getString(1);
                Integer floor = resultSet.getInt(2);
                Integer room = resultSet.getInt(3);
                String listOutput = "Workplace: " + wplaceID +" Floor: "+floor+" Room: "+room;
                System.out.println(listOutput);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int saveBookingDesk(BookingDesk newBooking) {
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass)) {
            String bkDesk = "UPDATE workplaces\tSET dateFrom = '?', dateTo = '?', occupied = 'Y', userID = ? WHERE wplaceID = ?;";

            PreparedStatement statement = conn.prepareStatement(bkDesk);
            statement.setString(1, newBooking.getWplaceID());
            statement.setString(6, dateFrom);
            statement.setString(7, dateTo);
            statement.setInt(8, userID);

            int rowsInserted = statement.executeUpdate();
            if(rowsInserted > 0){
                System.out.println("Congratulations! Your booking is confirmed");
            } else {
                System.out.println("Ups! Try again...Please check spelling");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }



}
