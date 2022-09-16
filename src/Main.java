
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Main {
    //variable for checking current user
    static public int currentUserId = 0;
    //static String topic;
    static char runAgain = 'y';


    //DataBase Class
    static DBConnection dataBase = new DBConnection();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        char tryAgain = 'y';
        while (currentUserId == 0 && tryAgain == 'y') {
            System.out.println("What you want to do?");
            System.out.println("l - login");
            System.out.println("c - create an account");
            char action = scanner.nextLine().charAt(0);

            if (action == 'l') {
                login();
            } else if (action == 'c') {
                createUser();
            }

            if (currentUserId > 0) {
                while (runAgain == 'y') {
                    System.out.println("What would you like to do?");
                    System.out.println("l - list free Desks");
                    System.out.println("b - book a Desk");
                    System.out.println("c - cancel booking");
                    char choice = scanner.nextLine().charAt(0);

                    if (choice == 'b') {
                        bookDesk();
                    }/* else if (choice == 'c') {
                        cancelDesk();
                    } else
                    if (choice == 'l') {
                        listDesk();
                    }*/

                    System.out.println("Do you want to do something more? y/n");
                    runAgain = scanner.nextLine().charAt(0);
                }
            } else {
                System.out.println("Incorrect user name or password");
                System.out.println("Try again? y/n");
                tryAgain = scanner.nextLine().charAt(0); //FIX no input possible
            }
        }
    }

    public static void login() {
        UserRegistration currentUser = new UserRegistration();

        System.out.println("Enter username");
        currentUser.setUserName(scanner.nextLine());

        System.out.println("Enter password");
        currentUser.setPassword(scanner.nextLine());

        //DataBase class method for login check returns current Users ID Nr.
        int userId = dataBase.checkLogin(currentUser.getUserName(), currentUser.getPassword());
        if (userId > 0) {
            System.out.println("You have logged in successfully!");
            currentUserId = userId;
        }
    }

    public static void createUser() {
        UserRegistration newUser = new UserRegistration();
        System.out.println("Enter username");
        newUser.setUserName(scanner.nextLine());

        //to create a method that we can use for others as well
        Pattern pattern = Pattern.compile("^[a-zA-Z\\d]{3,20}$");
        Matcher matcher = pattern.matcher(newUser.getUserName());
        while (!matcher.matches()) {
            System.out.println("Please enter a valid username! It should be at least 3 characters!");
            newUser.setUserName(scanner.nextLine());
            pattern = Pattern.compile("^[a-zA-Z\\d]{3,20}$");
            matcher = pattern.matcher(newUser.getUserName());
        }

        //DataBase class method for username check returns 1 if true, 0 if false
        int userId = dataBase.checkUser(newUser.getUserName());

        while (userId > 0) {
            System.out.println("Username already exists! Try another one!");
            newUser.setUserName(scanner.nextLine());
            pattern = Pattern.compile("^[a-zA-Z\\d+_.!-]]{3,20}$");
            matcher = pattern.matcher(newUser.getUserName());
            while (!matcher.matches()) {

                System.out.println("Please enter a valid username! It should be at least 3 characters!");
                newUser.setUserName(scanner.nextLine());
                pattern = Pattern.compile("^[a-zA-Z\\d+_.!-]]{3,20}$");
                matcher = pattern.matcher(newUser.getUserName());
            }
            userId = dataBase.checkUser(newUser.getUserName());

        }

        System.out.println("Enter password");
        newUser.setPassword(scanner.nextLine());

        pattern = Pattern.compile("^[a-zA-Z\\d+_.!-]{3,20}$");
        matcher = pattern.matcher(newUser.getPassword());
        while (!matcher.matches()) {
            System.out.println("Please enter a valid password! It should be at least 3 characters!");
            newUser.setPassword(scanner.nextLine());
            pattern = Pattern.compile("^[a-zA-Z\\d+_.!-]{3,20}$");
            matcher = pattern.matcher(newUser.getPassword());
        }

        System.out.println("Enter full name");
        newUser.setFullName(scanner.nextLine());

        pattern = Pattern.compile("^([A-Za-z]*((\\s)))+[A-Za-z]*$");
        matcher = pattern.matcher(newUser.getFullName());
        while (!matcher.matches()) {
            System.out.println("Please enter valid first name and last name!");
            newUser.setFullName(scanner.nextLine());
            pattern = Pattern.compile("^([A-Za-z]*((\\s)))+[A-Za-z]*$");
            matcher = pattern.matcher(newUser.getFullName());
        }

        System.out.println("Enter email");
        newUser.setUserEmail(scanner.nextLine());
        pattern = Pattern.compile("^[A-Za-z\\d+_.-]+@(.+)$");
        matcher = pattern.matcher(newUser.getUserEmail());
        while (!matcher.matches()) {
            System.out.println("Please enter valid e-mail!");
            System.out.println("Enter email");
            newUser.setUserEmail(scanner.nextLine());
            pattern = Pattern.compile("^[A-Za-z\\d+_.-]+@(.+)$");
            matcher = pattern.matcher(newUser.getUserEmail());
        }
        System.out.println("What is your role: m - manager | e - employee");
        newUser.setUserRole(scanner.nextLine());
        pattern = Pattern.compile("[me]", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(newUser.getUserRole());
        while (!matcher.matches()) {
            System.out.println("Please make a valid choice");
            System.out.println("What is your role: m - manager | e - employee");
            newUser.setUserRole(scanner.nextLine());
            pattern = Pattern.compile("[me]", Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(newUser.getUserRole());
        }

        //CurrentUserID returns new userID Nr.
        currentUserId = dataBase.createUser(newUser.getUserName(), newUser.getPassword(), newUser.getFullName(), newUser.getUserEmail(), newUser.getUserRole());

        if (currentUserId > 0) {
            System.out.println("You have created an account successfully!");
        }
    }

    public static void listDesk () {
        dataBase.readListDesk();
    }

    private static String getMatchedPattern(String inputMessage, String warnMessage, String pattern) {
        String inputValue;
        System.out.println(inputMessage);

        inputValue = scanner.nextLine();

        Pattern myPattern = Pattern.compile(pattern);
        Matcher myMatcher = myPattern.matcher(inputValue);

        while (!myMatcher.matches()) {
            System.out.println(warnMessage);
            inputValue = scanner.nextLine();
            myPattern.matcher(inputValue);
        }
        return inputValue;
    }
    public static void bookDesk () {
        BookingDesk newBooking = new BookingDesk();

//        System.out.println("Please enter Workplace ID");
//        newBooking.setWplaceID(scanner.nextLine());

//        Pattern pattern = Pattern.compile("\\d{6}");
//        Matcher matcher = pattern.matcher(newBooking.getWplaceID());
//        while (!matcher.matches()) {
//            System.out.println("Please check Workplace ID! It should be 6 digits");
//            newBooking.setWplaceID(scanner.nextLine());
//            pattern = Pattern.compile("\\d{6}");
//            matcher = pattern.matcher(newBooking.getWplaceID());
//        }

        newBooking.setWplaceID(getMatchedPattern("Please enter Workplace ID","Please check Workplace ID! It should be 6 digits","\\d{6}"));
        newBooking.setOccupied("Y");

        System.out.println("Please enter Date From: YYYYMMDD");
        newBooking.setDateFrom(scanner.nextLine());

        Pattern pattern1 = Pattern.compile("\\d{8}");
        Matcher matcher1 = pattern1.matcher(newBooking.getDateFrom());
        while (!matcher1.matches()) {
            System.out.println("Please check DateFrom! It should be in YYYYMMDD format");
            newBooking.setDateFrom(scanner.nextLine());
            pattern1 = Pattern.compile("\\d{8}");
            matcher1 = pattern1.matcher(newBooking.getDateFrom());
        }

        System.out.println("Please enter Date To: YYYYMMDD");
        newBooking.setDateTo(scanner.nextLine());

        Pattern pattern2 = Pattern.compile("\\d{8}");
        Matcher matcher2 = pattern2.matcher(newBooking.getDateTo());
        while (!matcher2.matches()) {
            System.out.println("Please check To! It should be in YYYYMMDD format");
            newBooking.setDateTo(scanner.nextLine());
            pattern2 = Pattern.compile("\\d{8}");
            matcher2 = pattern2.matcher(newBooking.getDateTo());
        }

        newBooking.setUserID(7); //(dataBase.checkUser(userID.getUserID()));  //TO TEST CAREFULLY!!!*/
// Save as Nellija asked  into DataBase
//        dataBase.saveBookingDesk(newBooking);
        //THANK YOU MESSAGE
    }

}
