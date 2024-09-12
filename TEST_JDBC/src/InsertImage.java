import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class InsertImage {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "shobhit2002";
        String path = "C:\\Users\\Shobit\\OneDrive\\Pictures\\123.jpg";
        String query = "INSERT INTO img(image_data) VALUES(?);";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded Successfully");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver failed: " + e.getMessage());
            return; // Exit if driver fails
        }

        try (Connection con = DriverManager.getConnection(url, username, password);
             FileInputStream fileInputStream = new FileInputStream(path);
             PreparedStatement pr = con.prepareStatement(query)) {

            System.out.println("Database connected Successfully");

            byte[] arr = new byte[fileInputStream.available()];
            fileInputStream.read(arr);
            pr.setBytes(1, arr);

            int rf = pr.executeUpdate();
            if (rf > 0) {
                System.out.println("Image Insertion Successfully");
            } else {
                System.out.println("Image Insertion Failed");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
