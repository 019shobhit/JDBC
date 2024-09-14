package HospitalManagementSystem;

import javax.print.Doc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url= "jdbc:mysql://localhost:3306/hospital";
    private static final String userName= "root";
    private static final String password= "shobhit2002";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try{
            Connection con = DriverManager.getConnection(url,userName,password);
            Patient patient = new Patient(con,sc);
            Doctor doctor = new Doctor(con);

            while (true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctor");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.print("Enter Your choice: ");
                int choice = sc.nextInt();

                switch (choice){
                    case 1:
                        // Add Patient
                        patient.addPatient();
                        break;
                    case 2:
                        //View Patient
                        patient.viewPatient();
                        break;
                    case 3:
                        // View Doctor
                        doctor.viewDoctors();
                        break;
                    case 4:
                        // Book Appointment
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Enter Valid Choice !!!");
                }
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
