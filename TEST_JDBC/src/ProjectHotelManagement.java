import java.sql.*;
import java.util.Scanner;

public class ProjectHotelManagement {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "shobhit2002";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver Not Loaded: " + e.getMessage());
        }

        try {
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt = con.createStatement();

            while (true) {
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                Scanner sc = new Scanner(System.in);
                System.out.println("1. Reservation a room");
                System.out.println("2. View Reservation");
                System.out.println("3. Get room Number");
                System.out.println("4. update Reservation");
                System.out.println("5. delete Reservation");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        reserveRoom(con, stmt, sc);
                        break;
                    case 2:
                        viewReservations(con, stmt);
                        break;
                    case 3:
                        getRoomNumber(con, stmt, sc);
                        break;
                    case 4:
                        updateReservation(con, stmt, sc);
                        break;
                    case 5:
                        deletereservation(con, stmt, sc);
                        break;
                    case 0:
                        exit();
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice! ---> Please try again");
                }
            }
        } catch (SQLException e) {
            System.out.println("DataBase not connected: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void reserveRoom(Connection con, Statement stmt, Scanner sc) {
        try {
            System.out.print("Enter guest name: ");
            String g_name = sc.next();
            sc.nextLine();
            System.out.print("Enter room Number: ");
            int r_no = sc.nextInt();
            System.out.print("Enter contact Number: ");
            String c_no = sc.next();

            String sql = "INSERT INTO reservations(guest_name,room_number,contact_number) " +
                    "VALUES('" + g_name + "'," + r_no + ",'" + c_no + "');";
            int rf = stmt.executeUpdate(sql);

            if (rf > 0) {
                System.out.println("\t\t\t\t\t\t\t\t.....Reservation Successful!.....");
            } else {
                System.out.println("Reservation failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewReservations(Connection con, Statement stmt) throws SQLException {
        String sql = "SELECT reservation_id,guest_name,room_number,contact_number,reservation_date FROM reservations;";
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("Current Reservation");
        System.out.println("+----------------+--------------+-------------+----------------+------------------+");
        System.out.println("| Reservation ID |  Guest Name  | Room Number | Contact Number | Reservation Date |");
        System.out.println("+----------------+--------------+-------------+----------------+------------------+");

        while (rs.next()) {
            int reservationId = rs.getInt("reservation_id");
            String guestName = rs.getString("guest_name");
            int roomNumber = rs.getInt("room_number");
            String contactNumber = rs.getString("contact_number");
            String reservationDate = rs.getString("reservation_date").toString();

            System.out.printf(" %-14d | %-12s | %-11d | %-14s | %-16s  |\n"
                    , reservationId, guestName, roomNumber, contactNumber, reservationDate);
        }
        System.out.println("+----------------+--------------+-------------+----------------+------------------+");
    }

    public static void getRoomNumber(Connection con, Statement stmt, Scanner sc) {
        try {
            System.out.print("Enter Reservation ID: ");
            int r_id = sc.nextInt();
//            System.out.print("Enter guest name: ");
//            String g_name = sc.next();

            String sql = "SELECT room_number from reservations WHERE reservation_id =" + r_id;
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                int r_no = rs.getInt("room_number");
                String g_name = rs.getString("guest_name");
                System.out.println("Room number for Reservation Id is: " + r_id + "\n\t Name is: " +
                        g_name + " \n Room No. is: " + r_no);
            } else {
                System.out.println("Reservation not found for the given ID and guest name.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateReservation(Connection con, Statement stmt, Scanner sc) {
        try {
            System.out.print("Enter reservation ID to Update details: ");
            int g_id = sc.nextInt();
            sc.nextLine();

            if (!reservationExist(con, stmt, g_id)) {
                System.out.println("Reservation not found for the given ID");
                return;
            }
            System.out.print("Enter new Guest name: ");
            String g_name = sc.nextLine();
            System.out.print("Enter new room Number:");
            int r_no = sc.nextInt();
            System.out.print("Enter new Contact Number:");
            String c_no = sc.next();

            String sql = "UPDATE reservations SET guest_name = '" + g_name
                    + "',room_number = " + r_no + ",contact_number = '" + c_no + "' WHERE guest_id = " + g_id + ";";

            int rf = stmt.executeUpdate(sql);
            if (rf > 0) {
                System.out.println("\t\t\t\t\t\t\t\t\t ....Reservation Update Successfully....");
            } else {
                System.out.println("Reservation update failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private static void deletereservation(Connection con, Statement stmt, Scanner sc) {
        try {
            System.out.print("Enter Reservation ID to delete: ");
            int r_id = sc.nextInt();

            if (!reservationExist(con, stmt, r_id)) {
                System.out.println("Reservation is not found for the given id");
                return;
            }
            String sql = "DELETE fROM reservations WHERE reservation_id =" + r_id + ";";
            int rf = stmt.executeUpdate(sql);

            if (rf > 0) {
                System.out.println("\t\t\t\t\t\t\t\t\t ....Reservation Deleted Successfully....");
            } else {
                System.out.println("Reservation deletion failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static boolean reservationExist(Connection con, Statement stmt, int id) {
        try {
            String sql = "SELECT reservation_id FROM reservations WHERE reservation_id = " + id + ";";
            ResultSet rs = stmt.executeQuery(sql);

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private static void exit() throws InterruptedException {
        System.out.print("Exiting System");
        int i = 5;
        while (i != 0) {
            System.out.print(".");
            Thread.sleep(300);
            i--;
        }
        System.out.println();
        System.out.println();
        System.out.print("\t\t\t\t\t\t\t\t\t.......ThankYou for using HOTEL MANAGEMENT SYSTEM....... ");


    }
}