import java.sql.*;

public class DeleteData {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "shobhit2002";
        String query = "DELETE FROM employees WHERE name = 'xyz';";

        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded Successfully");
        }
        catch (ClassNotFoundException e){
            System.out.println("Driver failed: "+ e.getMessage());
        }

        try{
            Connection con = DriverManager.getConnection(url,username,password);
            System.out.println("Database connected Successfully");
            Statement stmt = con.createStatement();
            int rf = stmt.executeUpdate(query);

            if(rf > 0){
                System.out.println("Deletion data Successful with "+rf+" row(s) affected");
            }
            else{
                System.out.println("Deletion Faild! ");
            }
            stmt.close();
            con.close();
            System.out.println();
            System.out.println("Connection closed Successfully");

        }
        catch (SQLException e){
            System.out.println("Connection faild: "+e.getMessage());
        }
    }
}
