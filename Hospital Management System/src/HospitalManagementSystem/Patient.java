package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection con;
    private Scanner sc;

    public Patient(Connection con , Scanner sc){
        this.con = con;
        this.sc =sc;
    }

    public void addPatient(){
        System.out.print("Enter Patient Name: ");
        String name = sc.next();
        sc.nextLine();
        System.out.print("Enter Patient Age: ");
        int age = sc.nextInt();
        System.out.print("Enter Patient Gender: ");
        String gender = sc.next();

        try{
            String query = "insert into patients(name,age,gender) values(?,?,?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,name);
            pst.setInt(2,age);
            pst.setString(3,gender);
            int rf = pst.executeUpdate();
            if(rf>0){
                System.out.println("Patient Added Successfully!");
            }else{
                System.out.println("Failed to add Patient");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void viewPatient(){
        String query = "Select * from patients";
        try{
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+------------------------+------------+-------------+");
            System.out.println("| Patient ID | Patient Name           | Age        | Gender      |");
            System.out.println("+------------+------------------------+------------+-------------+");
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                System.out.printf("| %-10s | %-22s | %-10s | %-11s |\n",id,name,age,gender);
                System.out.println("+------------+------------------------+------------+-------------+");
            }
        }
        catch(SQLException e){
            e.getStackTrace();
        }
    }
    public boolean getPatientById(int id){
        String query = "SELECT * FROM patients WHERE  id = ?";
        try{
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                return  true;
            }else{
                return false;
            }
        }
        catch (SQLException e){
            e.getStackTrace();
        }
        return false;
    }


}
