package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url= "jdbc:mysql://localhost:3306/hospital";
    private static final String userName= "root";
    private static final String password= "shobhit2002";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found!");
            return;
        }

        try (Connection con = DriverManager.getConnection(url, userName, password)) {
            Patient patient = new Patient(con, sc);
            Doctor doctor = new Doctor(con);

            while (true) {
                System.out.println("                      ............HOSPITAL MANAGEMENT SYSTEM.............            ");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctor");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.print("Enter Your choice: ");

                if (!sc.hasNextInt()) {
                    System.out.println("Invalid input! Please enter a number.");
                    sc.next();  // Consume invalid input
                    continue;
                }

                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        // Add Patient
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        // View Patient
                        patient.viewPatient();
                        System.out.println();
                        break;
                    case 3:
                        // View Doctor
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        // Book Appointment
                        bookAppointment(patient, doctor, con, sc);
                        System.out.println();
                        break;
                    case 5:
                        System.out.println(" ...THANK YOU FOR USING HOSPITAL MANAGEMENT ...");
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice! Please enter a valid option.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection con, Scanner sc) {
        try {
            System.out.print("Enter Patient ID: ");
            int patientId = sc.nextInt();
            System.out.print("Enter Doctor ID: ");
            int doctorID = sc.nextInt();
            System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
            String appointmentDate = sc.next();

            // Input validation for date format can be added here

            if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorID)) {
                if (checkDoctorAvailability(doctorID, appointmentDate, con)) {
                    String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";

                    try (PreparedStatement pst = con.prepareStatement(appointmentQuery)) {
                        pst.setInt(1, patientId);
                        pst.setInt(2, doctorID);
                        pst.setString(3, appointmentDate);

                        int result = pst.executeUpdate();
                        if (result > 0) {
                            System.out.println("Your appointment is booked for: " + appointmentDate);
                        } else {
                            System.out.println("Failed to book your appointment.");
                        }
                    }
                } else {
                    System.out.println("Doctor is not available on this date. Please choose another date.");
                }
            } else {
                System.out.println("Either doctor or patient doesn't exist!");
            }
        } catch (SQLException e) {
            System.out.println("Error during booking: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }

    public static boolean checkDoctorAvailability(int doctorID, String appointmentDate, Connection con) {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, doctorID);
            pst.setString(2, appointmentDate);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count == 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking doctor availability: " + e.getMessage());
        }
        return false;
    }
}
