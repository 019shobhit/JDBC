import java.sql.*;

public class TransactionHandling {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "shobhit2002";
        String withdrawQuery = "UPDATE accounts SET balance = balance - ? WHERE acc_number = ? ";
        String depositQuery = "UPDATE accounts SET balance = balance + ? WHERE acc_number = ? ";

        Connection con = null;
        PreparedStatement withdrawStmt = null;
        PreparedStatement depositStmt = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");

            // Establish the connection
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected successfully");

            // Start transaction
            con.setAutoCommit(false);

            try {
                // Prepare the SQL statements
                withdrawStmt = con.prepareStatement(withdrawQuery);
                depositStmt = con.prepareStatement(depositQuery);

                // Set parameters for withdraw statement
                withdrawStmt.setDouble(1, 500);
                withdrawStmt.setString(2, "account1");

                // Set parameters for deposit statement
                depositStmt.setDouble(1, 500);
                depositStmt.setString(2, "account2");

                // Execute the statements
                withdrawStmt.executeUpdate();
                depositStmt.executeUpdate();

                // Commit the transaction
                con.commit();
                System.out.println("Transaction successful!");
            } catch (SQLException e) {
                // Rollback in case of error
                if (con != null) {
                    try {
                        con.rollback();
                    } catch (SQLException rollbackEx) {
                        System.out.println("Rollback failed: " + rollbackEx.getMessage());
                    }
                }
                System.out.println("Transaction failed: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
        } finally {
            // Close resources in the finally block
            try {
                if (withdrawStmt != null) withdrawStmt.close();
                if (depositStmt != null) depositStmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Failed to close resources: " + e.getMessage());
            }
        }
    }
}
