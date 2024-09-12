import java.sql.*;
public class RetrieveData {
    public static void main(String[] args) throws ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "shobhit2002";
        String query = "SELECT * FROM EMPLOYEES;";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver Loaded");
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection con = DriverManager.getConnection(url,username,password);
            System.out.println("Connection Established Successfully");
            Statement st = con.createStatement();
            ResultSet res =  st.executeQuery(query);
            while(res.next()){
                int id = res.getInt("id");
                String name = res.getString("name");
                String job_role = res.getString("job_title");
                double salery = res.getDouble("salary");
                System.out.println();
                System.out.print("Id: "+ id);
                System.out.print("\tName: "+name);
                System.out.print("\tJob Role: "+job_role);
                System.out.print("\tSalary: "+salery);
            }
            res.close();
            st.close();
            con.close();
        }
        catch(SQLException e){
            System.out.println("Exception Caught: "+e.getMessage());
        }
    }
}
