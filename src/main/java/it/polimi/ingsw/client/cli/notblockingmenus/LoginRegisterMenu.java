package it.polimi.ingsw.client.cli.notblockingmenus;

import it.polimi.ingsw.client.UsernamePasswordValidator;
import it.polimi.ingsw.client.cli.CliPrinter;
import it.polimi.ingsw.client.cli.StdinSingleton;
import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.client.controller.datastructure.UsrPwdContainer;

/**
 * This menus is used to ask the user if he wants to login or register and consequently to ask for user and
 * password and make the right callback
 */
public class LoginRegisterMenu extends BasicCLIMenu {

    public LoginRegisterMenu(ViewControllerCallbackInterface controller) {
        super("Do you want to login with an existing account or register?", controller);

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
     * @return a user password container which allows CLI to ask a proper DB info
     */
    private UsrPwdContainer readUsrPwd()
    {
        String nickname;
        String password;

        CliPrinter.println("Insert username:");
        nickname = StdinSingleton.nextLine();

        while(!UsernamePasswordValidator.validateUsername(nickname)) {
            CliPrinter.println("Please insert a valid nickname:");
            nickname = StdinSingleton.nextLine();
        }

        CliPrinter.println("Insert password:");
        password = StdinSingleton.nextLine();

        while(!UsernamePasswordValidator.validatePassword(password)) {
            CliPrinter.println("Please insert a valid password:");
            password = StdinSingleton.nextLine();
        }

        return new UsrPwdContainer(nickname, password);
    }
}

