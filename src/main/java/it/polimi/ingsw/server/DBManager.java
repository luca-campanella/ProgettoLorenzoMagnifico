package it.polimi.ingsw.server;

import it.polimi.ingsw.client.exceptions.LoginErrorEnum;
import it.polimi.ingsw.client.exceptions.LoginException;
import it.polimi.ingsw.client.exceptions.UsernameAlreadyInUseException;
import it.polimi.ingsw.utils.Debug;

import java.sql.*;

/**
 * This is the singleton class that manages the database with usernames and passwords
 */
public  class DBManager {

    private static DBManager instance = null;

    private static final String url = "jdbc:sqlite:users.db";

    private static Connection conn;

    /**
     * Private constructor, basically it creates the database and the table if not already present
     * @throws SQLException if something goes wrong opening the database or creating it if it doesn't exists already
     */
    private DBManager() throws SQLException {

            try { // create a connection to the database
                conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
                close();
                throw e;
            }

            Debug.printVerbose("Connection to SQLite has been established.");

            // SQL statement for creating a new table
            String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                    + "	username text PRIMARY KEY NOT NULL,\n"
                    + "	password text NOT NULL\n"
                    + ");";

        Statement stmt = conn.createStatement();

        try {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            close();
            throw e;
        } finally {
            stmt.close();
        }

    }

    /**
     * It creates the singleton instance only if it doesn't exist already
     * @return DBManager the singleton instance
     * @throws SQLException if something goes wrong opening the database or creating it if it doesn't exists already
     */
    public static DBManager instance() throws SQLException {
        if (instance == null)
            instance = new DBManager();
        return instance;
    }

    /**
     * this method closes the connection to the database and consequently eliminates the singleton instance
     */
    public static void close()
    {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Debug.printError("cannot close db", ex);
        }

        instance = null;

        Debug.printVerbose("Db closed successfully");
    }

    /**
     * This method checks if the player can be logged into the system
     * @param username nickname / username
     * @param password password
     * @throws LoginException if the username doesn't exist in the db (NOT_EXISTING_USERNAME) or if the password is wrong (NOT_EXISTING_USERNAME). @see {@link it.polimi.ingsw.client.exceptions.LoginException}
     */
    public static void checkLogin(String username, String password) throws LoginException
    {
        if(!isRegistered(username))
            throw new LoginException(LoginErrorEnum.NOT_EXISTING_USERNAME);
        if(!isPasswordCorrect(username, password))
            throw new LoginException(LoginErrorEnum.WRONG_PASSWORD);
        Debug.printVerbose("Login of player " + username + " checked successfully");
    }

    /**
     * Private method to fully check if the player is present in the database with the right password
     * @param username nickname / username
     * @param password password
     * @return true if present with the correct password
     */
    private static boolean isPasswordCorrect(String username, String password) throws LoginException
    {
        String query =  "SELECT * FROM users WHERE username = ? AND password = ?";
        Boolean result = false;
        //TODO insert encryption in query with MD5(), with password=MD5(?)

        PreparedStatement pstmt;
        try {
             pstmt = conn.prepareStatement(query);
        } catch (SQLException e) {
            Debug.printError("Cannot prepare statement", e);
            throw new LoginException(LoginErrorEnum.DATABASE_ERROR, e.getMessage());
        }

        try {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            result = rs.next();

        } catch (SQLException e) {
            Debug.printError("Cannot execute query", e);
            throw new LoginException(LoginErrorEnum.DATABASE_ERROR, e.getMessage());

        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                Debug.printError("Cannot close statement", e);
            }
        }

        return result;
    }

    /**
     * Private method to check if a username is present in the db
     * @param username nickname / username
     * @return true if present
     */
    private static boolean isRegistered(String username) throws LoginException
    {
        String query =  "SELECT * FROM users WHERE username = ?";

        Boolean result = false;
        //TODO insert encryption in query with MD5(), with password=MD5(?)

        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(query);
        } catch (SQLException e) {
            Debug.printError("Cannot prepare statement", e);
            throw new LoginException(LoginErrorEnum.DATABASE_ERROR, e.getMessage());
        }

        try {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            result = rs.next();

        } catch (SQLException e) {
            Debug.printError("Cannot execute query", e);
            throw new LoginException(LoginErrorEnum.DATABASE_ERROR, e.getMessage());

        } finally {
            try {
                    pstmt.close();
            } catch (SQLException e) {
                Debug.printError("Cannot close statement", e);
            }
        }

        return result;
    }

    /**
     * method used to register a new username to the db
     * @param username nickname / username
     * @param password password
     * @throws UsernameAlreadyInUseException if the username is already in use
     */
    public static void register(String username, String password) throws UsernameAlreadyInUseException {

        try {
            if (isRegistered(username)) {
                Debug.printDebug("Username " + username + "already in use, can't register to the db");
                throw new UsernameAlreadyInUseException("Username " + username + "already in use, can't register to the db");
            }
        } catch (LoginException e) {
            //TODO handle this
            e.printStackTrace();
        }


        String sql = "INSERT INTO users(username,password) VALUES(?,?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            Debug.printError("Cannot add " + username + " to db", e);
        }

        Debug.printVerbose("player " + username + " registered successfully");
    }

    public static void main(String args[]) {

        Debug.instance(Debug.LEVEL_VERBOSE);
        try {
            DBManager.instance();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            DBManager.register("prova", "prova");
        } catch (UsernameAlreadyInUseException e) {
            Debug.printError(e);
        }

        try {

            DBManager.checkLogin("prova", "prova");
        } catch (LoginException e) {
            e.printStackTrace();
            Debug.printVerbose("0" + e.getErrorType().toString());
        }
        try {
            DBManager.checkLogin("prova1", "prova");
        } catch (LoginException e) {
            e.printStackTrace();
            Debug.printVerbose("1" + e.getErrorType().toString());
        }
        try {
            DBManager.checkLogin("prova", "prova2");
        } catch (LoginException e) {
            e.printStackTrace();
            Debug.printVerbose("2" + e.getErrorType().toString());
        }
    }
}