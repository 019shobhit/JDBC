import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertData {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "shobhit2002";
        String query = "INSERT INTO employees (id,name,job_title,salary) " +
                "VALUES(7,'abc','MERN Developer',10000.00)," +
                "(6,'xyz','MERN Developer',12000.00);";

        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver Loaded Successfully");
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection con = DriverManager.getConnection(url,username,password);
            System.out.println("Database connected Successfully");
            Statement stmt = con.createStatement();
            int rf = stmt.executeUpdate(query);

            if(rf > 0){
                System.out.println("Insert data Successful with "+rf+" row(s) affected");
            }
            else{
                System.out.println("DInsertion Faild! ");
            }

            stmt.close();
            con.close();
            System.out.println();
            System.out.println("Connection closed Successfully");

        }
        catch (SQLException e){
            System.out.println("Exception Caught: "+e.getMessage());
        }
    }
}
