
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Main {
    static public int currentUserId = 0;
    static public String currentUserRole;
    static char runAgain = 'y';
    static DBConnection dataBase = new DBConnection();
    static Scanner scanner = new Scanner(System.in);
    static UserRegistration currentUser = new UserRegistration();
    static BookingDesk newBooking = new BookingDesk();

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



            if (currentUserId > 0 && currentUserRole.equals("e")) {
                while (runAgain == 'y') {
                    System.out.println("What would you like to do?");
                    System.out.println("l - list free Desks");
                    System.out.println("b - book a Desk");
                    System.out.println("c - cancel booking");
                    char choice = scanner.nextLine().charAt(0);
                    if (choice == 'b') {
                        bookDesk();
                    } else if (choice == 'c') {
                        deleteBooking();
                    } else if (choice == 'l') {
                        listDesk();
                    }

                    System.out.println("Any other action? y/n");
                    runAgain = scanner.nextLine().charAt(0);
                }
            } else if (currentUserId > 0 && currentUserRole.equals("m")) {
                    while (runAgain == 'y') {
                    System.out.println("What would you like to do?");
                    System.out.println("l - list free Desks");
                    System.out.println("n - list occupied Desks");
                    System.out.println("a - add a Desk");
                    System.out.println("r - remove a Desk");
                    char choice = scanner.nextLine().charAt(0);
                    if (choice == 'l') {
                        listDesk();
                    } else if (choice == 'n') {
                        listOccDesk();
                    } else if (choice == 'a') {
                        addDesk();
                    } else if (choice == 'r') {
                        removeDesk();
                    }

                    System.out.println("Any other action? y/n");
                    runAgain = scanner.nextLine().charAt(0);
                }

            }else if (currentUserId <0) {
                System.out.println("Incorrect user name or password");
                System.out.println("Try again? y/n");
                tryAgain = scanner.nextLine().charAt(0);
            }
        }
    }

    public static void login() {

        System.out.println("Enter username");
        currentUser.setUserName(scanner.nextLine());

        System.out.println("Enter password");
        currentUser.setPassword(scanner.nextLine());

        //DataBase class method for login check returns current Users ID Nr.
        int userId = dataBase.checkLogin(currentUser.getUserName(), currentUser.getPassword());
        if (userId > 0) {
            System.out.println("You have logged in successfully!");
            currentUserId = userId;
        }currentUserRole = dataBase.getUserRole(currentUser.getUserName());
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
            myPattern = Pattern.compile(pattern);
            myMatcher = myPattern.matcher(inputValue);
        }
        return inputValue;
    }
    public static void createUser() {
        UserRegistration newUser = new UserRegistration();

        newUser.setUserName(getMatchedPattern("Enter username", "Please enter a valid username! It should be at least 3 characters!","^[a-zA-Z\\d]{3,20}$"  ));

        //DataBase class method for username check returns 1 if true, 0 if false
        int userId = dataBase.checkUser(newUser.getUserName());
        while (userId > 0) {
            newUser.setUserName(getMatchedPattern("Username already exists! Try another one!","Please enter a valid username! It should be at least 3 characters!","^[a-zA-Z\\d+_.!-]]{3,20}$"));
            userId = dataBase.checkUser(newUser.getUserName());
        }

        newUser.setPassword(getMatchedPattern("Enter password","Please enter a valid password! It should be at least 3 characters!","^[a-zA-Z\\d+_.!-]{3,20}$"));

        newUser.setFullName(getMatchedPattern("Enter full name","Please enter valid first name and last name!","^([A-Za-z]*((\\s)))+[A-Za-z]*$" ));

        newUser.setUserEmail(getMatchedPattern("Enter email", "Please enter valid e-mail!","^[A-Za-z\\d+_.-]+@(.+)$"));

        newUser.setUserRole(getMatchedPattern("What is your role: m - manager | e - employee","Please make a valid choice","[me]"));

        //CurrentUserID returns new userID Nr.
        currentUserId = dataBase.createUserDB(newUser);

        if (currentUserId > 0) {
            System.out.println("You have created an account successfully!");
        }
    }
    public static void listDesk () {
        dataBase.readListDesk();
    }
    public static void bookDesk () {
        //BookingDesk newBooking = new BookingDesk();

        newBooking.setWplaceID(getMatchedPattern("Please enter Workplace ID","Please check Workplace ID! It should be 6 digits","\\d{6}"));

        newBooking.setOccupied("1");

        newBooking.setDateFrom(getMatchedPattern("Please enter Date From: YYYYMMDD", "Please check DateFrom! It should be in YYYYMMDD format", "\\d{8}"));

        newBooking.setDateTo(getMatchedPattern("Please enter Date To: YYYYMMDD", "Please check DateTo! It should be in YYYYMMDD format","\\d{8}"));

        newBooking.setUserID(dataBase.getUserID(currentUser.getUserName()));

        dataBase.saveBookingDesk(newBooking);
    }
    public static void deleteBooking () { //MVP2 - add validation of userID vs. WorkplaceID
        BookingDesk delBooking = new BookingDesk();

        delBooking.setWplaceID(getMatchedPattern("Please enter Workplace ID","Please check Workplace ID! It should be 6 digits","\\d{6}"));

        delBooking.setOccupied("0");

        delBooking.setDateFrom(null);

        delBooking.setDateTo(null);

        delBooking.setUserID(dataBase.getUserID(currentUser.getUserName()));  //CURRENT USER TO TEST CAREFULLY!!!

        dataBase.delBookingDesk(delBooking);
    }
    public static void listOccDesk () {
        dataBase.readListOccDesk();
    }
    public static String addDesk() {
        AddingDesk newDesk = new AddingDesk();

        newDesk.setFloor(parseInt(getMatchedPattern("Please add floor nr.", "Please check floor number! It should be 1 digit", "\\d{1}")));

        newDesk.setRoom(parseInt(getMatchedPattern("Please enter room nr.", "Please check room number! It should be max 2 digits", "\\d{1,2}")));

        newDesk.setDeskID(parseInt(getMatchedPattern("Please add DeskID", "Please check DeskID! It should be max 3 digits", "\\d{1,2,3}")));

        newDesk.setWplaceID("" + AddingDesk.getFloor() + AddingDesk.getRoom() + AddingDesk.getDeskID());

        newDesk.setOccupied("0");

        dataBase.saveAddingDesk(newDesk);
        return "";

    }
    public static void removeDesk () {
        AddingDesk delDesk = new AddingDesk();

        delDesk.setWplaceID(getMatchedPattern("Please enter Workplace ID","Please check Workplace ID! It should be 6 digits","\\d{6}"));

        dataBase.delWorkplace(delDesk);
    }


}
