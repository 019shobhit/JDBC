import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/college";
        String username = "root";
        String password = "shobhit2002";

        try(Connection con = DriverManager.getConnection(url,username,password)){
            System.out.println("Connected to the Database");
            System.out.println(con);
        }
        catch (SQLException e){
            System.err.println("Connection Faild: "+e.getMessage());
        }
    }
}