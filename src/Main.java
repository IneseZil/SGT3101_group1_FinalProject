import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    //variable for checking current user
    static public int currentUserId = 0;
    //static String topic;
    static char runAgain = 'y';


    //DataBase Class
    static DBConnection dataBase = new DBConnection();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (currentUserId == 0) {
            System.out.println("What you want to do?");
            System.out.println("l - login");
            System.out.println("c - create an account");
            char action = scanner.nextLine().charAt(0);

            if (action == 'l') {
                login();
            } else if (action == 'c') {
                createUser();
            }
            /*if (currentUserId > 0) {
                while (runAgain == 'y') {
                    System.out.println("What you want to do?");
                    System.out.println("b - book");
                    System.out.println("c - cancel");
                    char choice = scanner.nextLine().charAt(0);

                    if (choice == 'b') {
                        bookDesk();
                    } else if (choice == 'c') {
                        cancelDesk();
                    }
                    System.out.println("Do you want to do something more? y/n");
                    runAgain = scanner.nextLine().charAt(0);
                }
            } else {
                System.out.println("Incorrect login or no account!");
                System.out.println("Do you want to try again? y/n");
                nextTry = scanner.nextLine().charAt(0);
            }*/
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
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{3,20}$");
        Matcher matcher = pattern.matcher(newUser.getUserName());
        while (matcher.matches() == false) {
            System.out.println("Please enter a valid username! It should be at least 3 characters!");
            newUser.setUserName(scanner.nextLine());
            pattern = Pattern.compile("^[a-zA-Z0-9]{3,20}$");
            matcher = pattern.matcher(newUser.getUserName());
        }

        //DataBase class method for username check returns 1 if true, 0 if false
        int userId = dataBase.checkUser(newUser.getUserName());

        while (userId > 0) {
            System.out.println("Username already exists! Try another one!");
            newUser.setUserName(scanner.nextLine());
            pattern = Pattern.compile("^[a-zA-Z0-9+_.!-]]{3,20}$");
            matcher = pattern.matcher(newUser.getUserName());
            while (matcher.matches() == false) {

                System.out.println("Please enter a valid username! It should be at least 3 characters!");
                newUser.setUserName(scanner.nextLine());
                pattern = Pattern.compile("^[a-zA-Z0-9+_.!-]]{3,20}$");
                matcher = pattern.matcher(newUser.getUserName());
            }
            userId = dataBase.checkUser(newUser.getUserName());

        }

        System.out.println("Enter password");
        newUser.setPassword(scanner.nextLine());

        pattern = Pattern.compile("^[a-zA-Z0-9+_.!-]{3,20}$");
        matcher = pattern.matcher(newUser.getPassword());
        while (matcher.matches() == false) {
            System.out.println("Please enter a valid password! It should be at least 3 characters!");
            newUser.setPassword(scanner.nextLine());
            pattern = Pattern.compile("^[a-zA-Z0-9+_.!-]{3,20}$");
            matcher = pattern.matcher(newUser.getPassword());
        }

        System.out.println("Enter full name");
        newUser.setFullName(scanner.nextLine());

        pattern = Pattern.compile("^([A-Za-z]*((\\s)))+[A-Za-z]*$");
        matcher = pattern.matcher(newUser.getFullName());
        while (matcher.matches() == false) {
            System.out.println("Please enter valid first name and last name!");
            newUser.setFullName(scanner.nextLine());
            pattern = Pattern.compile("^([A-Za-z]*((\\s)))+[A-Za-z]*$");
            matcher = pattern.matcher(newUser.getFullName());
        }

        System.out.println("Enter email");
        newUser.setUserEmail(scanner.nextLine());
        pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        matcher = pattern.matcher(newUser.getUserEmail());
        while (matcher.matches() == false) {
            System.out.println("Please enter valid e-mail!");
            System.out.println("Enter email");
            newUser.setUserEmail(scanner.nextLine());
            pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
            matcher = pattern.matcher(newUser.getUserEmail());
        }
        System.out.println("What is your role: m - manager | e - employee");
        newUser.setUserRole(scanner.nextLine());
        pattern = Pattern.compile("[me]{1}", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(newUser.getUserRole());
        while (matcher.matches() == false) {
            System.out.println("Please make a valid choice");
            System.out.println("What is your role: m - manager | e - employee");
            newUser.setUserRole(scanner.nextLine());
            pattern = Pattern.compile("[me]{1}", Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(newUser.getUserRole());
        }

        //CurrentUserID returns new userID Nr.
        currentUserId = dataBase.createUser(newUser.getUserName(), newUser.getPassword(), newUser.getFullName(), newUser.getUserEmail(), newUser.getUserRole());

        if (currentUserId > 0) {
            System.out.println("You have created an account successfully!");
        }
    }
}
