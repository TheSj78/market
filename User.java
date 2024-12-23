import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * User
 * <p>
 * This class is a Parent class of both Seller and Customer.
 * It represents a singular User of the Marketplace.
 */
public class User implements Serializable {
    //declarations
    private String email;
    private String password;
    private final File userInfo = new File("userInfo.csv");

    //constructors
    public User(String email, String password) throws IOException {
        this.email = email;
        this.password = password;

        FileOutputStream fos = new FileOutputStream(userInfo, true);
        PrintWriter pw = new PrintWriter(fos);

        pw.flush();
        pw.println(email + "," + password);
        pw.close();
    }

//methods
    //getters

    // returns the email of the User
    public String getEmail() {
        return email;
    }

    // returns the password of the User
    public String getPassword() {
        return password;
    }

    //setters

    // sets the email of the user to the given email
    public void setEmail(String email) {
        this.email = email;
    }

    // sets the password of the user to the given password
    public void setPassword(String password) {
        this.password = password;
    }

    //other methods

    //deletes an account by setting fields to an empty field and removing it from the userInfo.csv file
    public void deleteAccount(String userType) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("loginInfo.csv"), StandardCharsets.UTF_8);
        lines.remove(email + "," + password + "," + userType);

        FileOutputStream fos = new FileOutputStream("loginInfo.csv", false);
        PrintWriter pw = new PrintWriter(fos);

        for (String line : lines) {
            pw.flush();
            pw.println(line);
        }
        pw.close();

        email = "";
        password = "";
    }

    public void editAccount(String newEmail, String newPassword, String userType) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("loginInfo.csv"), StandardCharsets.UTF_8);
        lines.set(lines.indexOf(email + "," + password + "," + userType), newEmail + "," +
                newPassword + "," + userType);

        FileOutputStream fos = new FileOutputStream("loginInfo.csv", false);
        PrintWriter pw = new PrintWriter(fos);

        for (String line : lines) {
            pw.println(line);
            pw.flush();
        }
        pw.close();

        email = newEmail;
        password = newPassword;
    }

    //allows users to view their dashboards, different for customers and sellers
    public void viewDashboard() throws IOException {
    }
}
