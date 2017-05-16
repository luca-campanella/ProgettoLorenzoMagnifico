package it.polimi.ingsw.server;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

/**
 * This is the class that manages the database with usernames and passords
 */
public class DBManager {
    /**
     * Connect to a sample database
     */
    private static void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:/resources/users.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        connect();
    }
}