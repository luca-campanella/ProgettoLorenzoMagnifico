package it.polimi.ingsw.client.cli.notblockingmenus;

import it.polimi.ingsw.client.cli.StdinSingleton;
import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.client.controller.datastructure.UsrPwdContainer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This menus is used to ask the user if he wants to login or register and consequently to ask for user and
 * password and make the right callback
 */
public class LoginRegisterMenu extends BasicCLIMenu {
    /**
     * Used to validate username with regex
     */
    private Pattern usernamePattern;
    private Pattern passwordPattern;
    private Matcher matcher;

    //todo change min lenght to 3
    private static final String USERNAME_PATTERN = "^[a-z0-9_-]{1,15}$";
    private static final String PASSWORD_PATTERN = "(?=\\S+$).{1,20}"; //cannot contain spaces

    public LoginRegisterMenu(ViewControllerCallbackInterface controller) {
        super("Do you want to login with an existing account or register?", controller);

        usernamePattern = Pattern.compile(USERNAME_PATTERN);
        passwordPattern = Pattern.compile(PASSWORD_PATTERN);

        addOption("login", "login with an existing account",
                    () -> this.login());
        addOption("register", "create a new account",
                () -> this.register());
    }

    private void login() {
        UsrPwdContainer usrPwdContainer = readUsrPwd();
        getController().callbackLogin(usrPwdContainer.getNickname(), usrPwdContainer.getPassword());
    }

    private void register(){
        UsrPwdContainer usrPwdContainer = readUsrPwd();
        getController().callbackCreateAccount(usrPwdContainer.getNickname(), usrPwdContainer.getPassword());
    }

    /**
     * this method allows CLI to ask proper Database's info to user.
     * @return
     */
    private UsrPwdContainer readUsrPwd()
    {
        String nickname, password;

        System.out.println("Insert username:");
        nickname = StdinSingleton.nextLine();

        while(!validate(nickname, usernamePattern)) {
            System.out.println("Please insert a valid nickname:");
            nickname = StdinSingleton.nextLine();
        }

        System.out.println("Insert password:");
        password = StdinSingleton.nextLine();

        while(!validate(password, passwordPattern)) {
            System.out.println("Please insert a valid password:");
            password = StdinSingleton.nextLine();
        }

        return new UsrPwdContainer(nickname, password);
    }


    /**
     * Validate username or password with regular expression
     * @param string the username or password for validation
     * @param pattern the initialized pattern
     * @return true valid username or password, false invalid
     */
    public boolean validate(final String string, Pattern pattern){

        matcher = pattern.matcher(string);
        return matcher.matches();

    }
}

