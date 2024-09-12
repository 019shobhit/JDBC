//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
import java.sql.*;


public class BatchProcessing {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "shobhit2002";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded Successfully!!!");
        }
        catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection con = DriverManager.getConnection(url,username,password);
            System.out.println("Connection Establish Successful !!");
            con.setAutoCommit(false);

            Statement stmt = con.createStatement();
            stmt.addBatch("INSERT INTO employees (id,name, job_title,salary) VALUES(20,'Akshu' , 'jar' , 50000) ");
            stmt.addBatch("INSERT INTO employees (name, job_title,salary) VALUES(21,'rohit' , 'jar2' ,35000) ");
            stmt.addBatch("INSERT INTO employees (name, job_title,salary) VALUES(22,'vikhayat' , 'jar3' ,10000) ");
            int[] res = stmt.executeBatch();
            con.commit();
            System.out.println("batch executed successfully!! ");



        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
