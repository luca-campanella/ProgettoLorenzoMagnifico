package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.LoginErrorEnum;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.exceptions.UsernameAlreadyInUseException;
import it.polimi.ingsw.utils.Debug;

import java.sql.*;

/**
 * This is the singleton class that manages the database with usernames and passords
 */
public  class DBManager {

    private static DBManager instance = null;

    private static final String url = "jdbc:sqlite:users.db";

    private static Connection conn;

    /**
     * Private constructor, basically it creates the database and the table if not already present
     */
    private DBManager() throws SQLException {

            // create a connection to the database
            conn = DriverManager.getConnection(url);

            Debug.printVerbose("Connection to SQLite has been established.");

            // SQL statement for creating a new table
            String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                    + "	username text PRIMARY KEY NOT NULL,\n"
                    + "	password text NOT NULL\n"
                    + ");";

            Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
            stmt.close();
    }

    /**
     * It creates the singleton instance only if it doesn't exist already
     * @return DBManager the singleton instance
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
     * @param username
     * @param password
     * @throws LoginException if the username doesn't exist in the db (NOT_EXISTING_USERNAME) or if the password is wrong (NOT_EXISTING_USERNAME). @see {@link it.polimi.ingsw.exceptions.LoginException}
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
     * @param username
     * @param password
     * @return true if present with the correct password
     */
    private static boolean isPasswordCorrect(String username, String password)
    {
        String query =  "SELECT * FROM users WHERE username = ? AND password = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Debug.printError("Cannot execute query", e);
        }

        return false;
    }

    /**
     * Private method to check if a username is present in the db
     * @param username
     * @return true if present
     */
    private static boolean isRegistered(String username)
    {
        String query =  "SELECT * FROM users WHERE username = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Debug.printError("Cannot execute query", e);
        }

        return false;
    }

    /**
     * method used to register a new username to the db
     * @param username
     * @param password
     * @throws UsernameAlreadyInUseException if the username is already in use
     */
    public static void register(String username, String password) throws UsernameAlreadyInUseException {

        if(isRegistered(username)) {
            Debug.printDebug("Username " + username + "already in use, can't register to the db");
            throw new UsernameAlreadyInUseException("Username " + username + "already in use, can't register to the db");
        }

        String sql = "INSERT INTO users(username,password) VALUES(?,?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Debug.printError("Cannot add " + username + " to db", e);
        }
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