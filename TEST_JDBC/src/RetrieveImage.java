import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

public class RetrieveImage {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "shobhit2002";
        String path = "C:\\Users\\Shobit\\OneDrive\\Pictures\\extracted_image.jpg"; // Specify the file name and extension
        String query = "SELECT image_data FROM img WHERE image_id = ?;";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver loading failed: " + e.getMessage());
            return;
        }

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement pr = con.prepareStatement(query);
             OutputStream os = new FileOutputStream(path)) {

            System.out.println("Database connected successfully");

            pr.setInt(1, 1);  // Specify the image_id to retrieve
            try (ResultSet rs = pr.executeQuery()) {
                if (rs.next()) {
                    byte[] data_arr = rs.getBytes("image_data");
                    os.write(data_arr);
                    System.out.println("Image found and saved at the given path");
                } else {
                    System.out.println("Image not found");
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
