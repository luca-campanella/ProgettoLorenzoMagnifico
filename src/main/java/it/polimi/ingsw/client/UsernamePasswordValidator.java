package it.polimi.ingsw.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to validate usernames or passwords, to check if they are ok
 */
public class UsernamePasswordValidator {
    /**
     * Used to validate username with regex
     */
    //todo change min lenght to 3
    private static final String USERNAME_PATTERN = "^[a-z0-9_-]{1,15}$";
    private static final String PASSWORD_PATTERN = "(?=\\S+$).{1,20}"; //cannot contain spaces

    private static final Pattern usernamePattern = Pattern.compile(USERNAME_PATTERN);
    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
    private static Matcher matcher;

    /**
     * Validate username with regular expression
     * @param username the username for validation
     * @return true valid username, false invalid
     */
    public static boolean validateUsername(final String username){
        matcher = usernamePattern.matcher(username);
        return matcher.matches();
    }

    /**
     * Validate password with regular expression
     * @param password the password for validation
     * @return true valid password, false invalid
     */
    public static boolean validatePassword(final String password){
        matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }

}
