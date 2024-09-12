import java.sql.*;
import java.util.Scanner;

public class PreparedSatement {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "shobhit2002";
        String query = "INSERT INTO employees(id,name,job_title,salary) values( ? , ? , ? , ?);";

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
            PreparedStatement pst =  con.prepareStatement(query);

            Scanner sc = new Scanner(System.in);
            int id = sc.nextInt();
            String  name = sc.nextLine();
            sc.nextLine();
            String job = sc.nextLine();
            double salary = sc.nextDouble();


            pst.setInt(1,id);
            pst.setString(2,name);
            pst.setString(3,job);
            pst.setDouble(4,salary);

            int rf = pst.executeUpdate();

            System.out.println("Rows affected : "+rf);

            pst.close();
            con.close();
            System.out.println("Connection colosed successfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
