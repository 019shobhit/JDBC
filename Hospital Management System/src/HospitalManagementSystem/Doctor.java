package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
        private Connection con;

        public Doctor(Connection con){
            this.con = con;
        }

        public void viewDoctors(){
            String query = "Select * from doctors";
            try{
                PreparedStatement pst = con.prepareStatement(query);
                ResultSet rs = pst.executeQuery();
                System.out.println("Doctors: ");
                System.out.println("+------------+------------------------+--------------------+");
                System.out.println("| Doctor ID  | Doctor Name            | Specialization     |");
                System.out.println("+------------+------------------------+--------------------+");
                while(rs.next()){
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String specialization = rs.getString("specialization");
                    System.out.printf("| %-10s | %-22s | %-18s |\n",id,name,specialization );
                    System.out.println("+------------+------------------------+--------------------+");
                }
            }
            catch(SQLException e){
                e.getStackTrace();
            }
        }
        public boolean getDoctorById(int id){
            String query = "SELECT * FROM doctors WHERE  id = ?";
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

