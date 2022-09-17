import java.sql.*;

public class DBConnection {

    static String dbURL = "jdbc:mysql://localhost:3306/java31_group1";
    static String dbUser = "root";
    static String dbPass = "1234";


    public int createUserDB(UserRegistration newUser) {
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass)) {
            String sql = "INSERT INTO users (userName, password, fullName, userEmail, userRole) VALUES (?,?,?,?,?);";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, newUser.getUserName());
            preparedStatement.setString(2, newUser.getPassword());
            preparedStatement.setString(3, newUser.getFullName());
            preparedStatement.setString(4, newUser.getUserEmail());
            preparedStatement.setString(5, newUser.getUserRole());
            preparedStatement.executeUpdate();

            String sqlID = "SELECT * FROM users WHERE userName ='" + newUser.getUserName() + "' and password ='" + newUser.getPassword() + "'";
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
        return 0;

    }


    public int checkUser(String userName) {

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
            String sql = "SELECT * FROM users WHERE userName ='" + userName + "' and password ='" + password + "'";

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

    public void readListDesk() {
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass)) {
            String listDesk = "SELECT * FROM workplaces WHERE occupied = '0'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(listDesk);

            while (resultSet.next()) {
                String wplaceID = resultSet.getString(1);
                int floor = resultSet.getInt(2);
                int room = resultSet.getInt(3);
                String listOutput = "Workplace: " + wplaceID + " Floor: " + floor + " Room: " + room;
                System.out.println(listOutput);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getUserID(String Name) {
        int usID = 0;
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass)) {
            String sql = "SELECT userID FROM users WHERE userName = '" + Name + "'";

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                usID = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usID;
    }

    public int saveBookingDesk(BookingDesk newBooking) {
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass)) {
            String bkDesk = "UPDATE workplaces\tSET occupied = ?, dateFrom = ?, dateTo = ?, userID = ? WHERE wplaceID = ?";

            PreparedStatement statement = conn.prepareStatement(bkDesk);
            statement.setString(1, newBooking.getOccupied());
            statement.setString(2, newBooking.getDateFrom());
            statement.setString(3, newBooking.getDateTo());
            statement.setInt(4, newBooking.getUserID());
            statement.setString(5, newBooking.getWplaceID());


            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Congratulations! Your booking is confirmed");
            } else {
                System.out.println("Ups! Try again...Please check spelling");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    public int delBookingDesk(BookingDesk delBooking) {
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass)) {
            String bkDesk = "UPDATE workplaces\tSET occupied = ?, dateFrom = ?, dateTo = ?, userID = ? WHERE wplaceID = ?";

            PreparedStatement statement = conn.prepareStatement(bkDesk);
            statement.setString(1, delBooking.getOccupied());
            statement.setString(2, delBooking.getDateFrom());
            statement.setString(3, delBooking.getDateTo());
            statement.setInt(4, delBooking.getUserID());
            statement.setString(5, delBooking.getWplaceID());


            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Your booking is cancelled");
            } else {
                System.out.println("Ups! Try again...Please check Workplace ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    public String getUserRole(String Name) {
        String usRole = "";
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass)) {
            String sql = "SELECT userRole FROM users WHERE userName = '" + Name + "'";

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                usRole = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usRole;
    }
    public void readListOccDesk() {
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass)) {
            String listDesk = "SELECT * FROM workplaces WHERE occupied = '1'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(listDesk);

            while (resultSet.next()) {
                String wplaceID = resultSet.getString(1);
                int floor = resultSet.getInt(2);
                int room = resultSet.getInt(3);
                String listOutput = "Workplace: " + wplaceID + " Floor: " + floor + " Room: " + room;
                System.out.println(listOutput);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void saveAddingDesk(AddingDesk newDesk) {
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass)) {
            String bkDesk = "INSERT INTO workplaces\tSET wplaceID = ?, floor = ?, room = ?, deskID = ?, occupied = ?";

            PreparedStatement statement = conn.prepareStatement(bkDesk);
            statement.setString(1, newDesk.getWplaceID());
            statement.setInt(2, newDesk.getFloor());
            statement.setInt(3, newDesk.getRoom());
            statement.setInt(4, newDesk.getDeskID());
            statement.setString(5,newDesk.getOccupied());


            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Congratulations! A Workplace is created");
            } else {
                System.out.println("Oops! Try again...");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int delWorkplace(AddingDesk delDesk) {
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass)) {
            String bkDesk = "DELETE FROM workplaces WHERE wplaceID = ?";

            PreparedStatement statement = conn.prepareStatement(bkDesk);
            statement.setString(1, delDesk.getWplaceID());


            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Your booking is cancelled");
            } else {
                System.out.println("Ups! Try again...Please check Workplace ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
        return 0;


    }

}
