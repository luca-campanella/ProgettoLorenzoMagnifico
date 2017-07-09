package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.cli.notblockingmenus.*;
import it.polimi.ingsw.client.controller.AbstractUIType;
import it.polimi.ingsw.client.controller.ClientMain;
import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.client.network.socket.packet.PlayerPositionEndGamePacket;
import it.polimi.ingsw.model.cards.VentureCardMilitaryCost;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.PersonalTile;
import it.polimi.ingsw.model.resource.MarketWrapper;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.model.resource.TowerWrapper;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * this class is the CLI. It allows players to do actions, play, discard leaders.
 */
public class CommandLineUI extends AbstractUIType {

    private ExecutorService pool;

    private WaitBasicCliMenu waitMenu;

    /**
     * This is the constructor of the class
     * @param controller is used to make callbacks on the controller ({@link ClientMain}
     */
    public CommandLineUI(ViewControllerCallbackInterface controller)
    {
        super(controller);
        pool = Executors.newFixedThreadPool(2);
        waitMenu = new WaitBasicCliMenu(controller);
    }

    /**
     * This method ask uses which network type wants to use.
     */
    public void askNetworkType()
    {
        Debug.printDebug("I am in CLI. Select NetworkType");

        NetworkTypePickerMenu menu = new NetworkTypePickerMenu(getController());

        pool.submit(menu);
    }

    /**
     * This method asks the user to pick one of the action spaces to put his family member in
     * Direction: {@link ClientMain} -> {@link AbstractUIType}
     * @param servantsNeededHarvest The servants needed by the user to harvest, Optional.empty() if the action is not valid
     * @param servantsNeededBuild The servants needed by the user to build, Optional.empty() if the action is not valid
     * @param servantsNeededCouncil The servants needed by the user to place on cuincil, Optional.empty() if the action is not valid
     * @param activeMarketSpaces The list of legal action spaces in the market
     * @param activeTowerSpaces the list of legal action spaces on the towers
     * @param availableServants the number of servants the user can spend to perform the action
     */
    @Override
    public void askWhichActionSpace(Optional<Integer> servantsNeededHarvest,
                                             Optional<Integer> servantsNeededBuild,
                                             Optional<Integer> servantsNeededCouncil,
                                             List<MarketWrapper> activeMarketSpaces,
                                             List<TowerWrapper> activeTowerSpaces,
                                            int availableServants) {
        Debug.printVerbose("AskWhichAction space called");
        ActionSpacePickerMenu menu = new ActionSpacePickerMenu(getController(),
                                                                 servantsNeededHarvest,
                                                                 servantsNeededBuild,
                                                                 servantsNeededCouncil,
                                                                 activeMarketSpaces,
                                                                 activeTowerSpaces,
                                                                    availableServants);

        Debug.printVerbose("Right before submit");
        pool.submit(menu);

    }

    /**
     * this method helps selectFamilyMember()'s method return if the color user wrote is right or not
     * this method should also receive the familyMembers list to match the input.
     * @param familyColorID is the color of a family member
     * @return true if the colors exist false instead
     */
    private boolean existingColors(String familyColorID){
        return (familyColorID.equalsIgnoreCase("yellow")||familyColorID.equalsIgnoreCase("red")||familyColorID.equalsIgnoreCase("green")||familyColorID.equalsIgnoreCase("neutral"));
    }

    /**
     * This method asks to the user if he wants to connect with an existing account or to create one.
     */
    public void askLoginOrCreate()
    {
        Debug.printDebug("I am in CLI.askLoginOrCreate");
        LoginRegisterMenu menu = new LoginRegisterMenu(getController());

        //pool.submit(menu);
        menu.login();//TODO change method login to private
    }


    /**
     * if an error occurs, this method printsit
     * @param title the title of the error
     * @param errorDescription the description of the erro
     */
    public void displayError(String title, String errorDescription)
    {
        CliPrinter.println("**Error** " + title + " - " + errorDescription);
    }

    /**
     * this method alerts user that there was an error somewhere. It doesn't handle the error
     * The error is a fatal one and after the user clicked ok the program terminates     *
     * @param title the title of the error
     * @param errorDescription the description of the error
     */
    @Override
    public void displayErrorAndExit(String title, String errorDescription) {
        this.displayError(title, errorDescription);
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            System.exit(0);
        }
        System.exit(0);
    }

    /**
     * This method is called by {@link ClientMain} to display an incoming chat message (Direction: {@link ClientMain} -> {@link AbstractUIType}; general direction: Server -> Client)
     *
     * @param senderNick is the nickname of the player senidng a message
     * @param msg is the message
     */
    @Override
    public void displayChatMsg(String senderNick, String msg) {

        CliPrinter.println("<" + senderNick + ">: " + msg);
    }

    /**
     * when the model need to call back the client to choose what effect applying
     * @param nameCard the name of the card that has different choices on the effects
     * @param choices the choices available
     * @return the number of the choice the player want
     */
    @Deprecated
    public int askChoice(String nameCard, ArrayList<String> choices, HashMap<ResourceTypeEnum, Integer> resourcePlayer){

        Debug.printDebug("you can choose different effect on the card " + nameCard);
        int cont = 0;
        for (String choice : choices){
            Debug.printDebug(cont + ") "+ choice);
            cont++;
        }
        Debug.printDebug(cont + ") NONE");
        Debug.printDebug("chose the effect to activate:");
        int numChoice;
        do{
            numChoice = StdinSingleton.getScanner().nextInt();
        } while (numChoice < 0 || numChoice>choices.size() );

        return numChoice;
        
    }

    /**
     * This method is called when the player has joined a room, but the game isn't started yet
     */
    @Override
    public void showWaitingForGameStart() {
        CliPrinter.println("Room joined, please wait for game to start...");
    }

    /**
     * This method is called by the controller after a leader is selected and the player has to wait for enemies to choose their
     */
    @Override
    public void showWaitingForLeaderChoices() {
        CliPrinter.println("Leader chose, please wait for other player(s) to choose...");
    }

    /**
     * This method is called by the controller after a personal tile is selected and the player
     * has to wait for enemies to choose their
     */
    @Override
    public void showWaitingForTilesChoices() {
        CliPrinter.println("Personal tile chose, please wait for other player(s) to choose...");
    }

    /**
     * Used when it's the turn of the user and he has to choose which action he wants to perform
     * This method will trigger either
     */
    @Override
    public void askInitialAction(boolean playedFamilyMember) {
        Debug.printVerbose("****wait menu state: " + waitMenu.getState());

        if(waitMenu.getState() == Thread.State.RUNNABLE || waitMenu.getState() == Thread.State.TIMED_WAITING)
            waitMenu.interrupt();

        InitialActionMenu menu = new InitialActionMenu(getController(), playedFamilyMember);

        pool.submit(menu);
    }

    /**
     * Asks what card to take when he has a free action due to a card that has such effect
     * @param towerWrapper the list of available spaces
     * @return the index of the choice
     */
    @Override
    public int askWhereToPlaceNoDiceFamilyMember(List<TowerWrapper> towerWrapper){
        //this method is used when a card like Abess is played;

        if(towerWrapper.size() == 1) {
            CliPrinter.println("You can only take the card of the space :\ntower number " +
                    towerWrapper.get(0).getTowerIndex() +
                    "\nFloor : " + towerWrapper.get(0).getTowerFloor() +
                    "\nwith " + towerWrapper.get(0).getServantNeeded()
                    + "servants needed");

            CliPrinter.println("I'm placing it over there");
            return 0;

        } else { //we have to ask the user for a choice
            CliOptionsHandler towerSpaceChooser = new CliOptionsHandler(towerWrapper.size());

            for (TowerWrapper towerIter : towerWrapper) {
                towerSpaceChooser.addOption("take the card on tower action space n "
                        + towerIter.getTowerIndex() + ", floor "
                        + towerIter.getTowerFloor() +
                        " with " + towerIter.getServantNeeded() + "servants needed");
            }
            return towerSpaceChooser.askUserChoice();
        }
    }

    /**
     * This method is called when a choice on a council gift should be perfomed by the ui
     *
     * @param options is a list with all options
     * @return to controller the index of the selected option, the choice the user made
     */
    @Override
    public int askCouncilGift(List<GainResourceEffect> options) {
        CliOptionsHandler optionsHandler = new CliOptionsHandler(options.size());
        optionsHandler.addEffectsArrayList(options);
        return optionsHandler.askUserChoice();
    }

    /**
     * This method is called when a choice on which effect to activate in a yellow card should be perfomed by the ui
     * @param possibleEffectChoices is a list with all options
     * @return the index of the chosen effect
     */
    @Override
    public int askYellowBuildingCardEffectChoice(List<ImmediateEffectInterface> possibleEffectChoices) {
        CliOptionsHandler optionsHandler = new CliOptionsHandler(possibleEffectChoices.size());
        optionsHandler.addEffectsArrayList(possibleEffectChoices);
        optionsHandler.addOption("Do not activate any effect and save resources for later");
        int choice = optionsHandler.askUserChoice();
        if(choice == possibleEffectChoices.size())
            return -1; // the player chose not to activate any effect
        return choice;
    }

    /**
     * This method is called when a choice on which cost to pay in a purple card should be perfomed by the ui
     * @param costChoiceResource the list of resources the player will pay if he chooses this option
     * @param costChoiceMilitary the cost he will pay on something conditioned
     * @return 0 if he chooses to pay with resources, 1 with military points
     */
    @Override
    public int askPurpleVentureCardCostChoice(List<Resource> costChoiceResource, VentureCardMilitaryCost costChoiceMilitary) {
        CliOptionsHandler optionsHandler = new CliOptionsHandler(2);
        StringBuilder optionResDescr = new StringBuilder();

        optionResDescr.append("pay the card with this resources: ");

        for(Resource resIter : costChoiceResource) {
            optionResDescr.append(resIter.getResourceFullDescript());
            optionResDescr.append(" | ");
        }

        optionsHandler.addOption(optionResDescr.toString());
        optionsHandler.addOption("Pay " + costChoiceMilitary.getResourceCost().getResourceFullDescript() + "(you fulfill the requirement of " + costChoiceMilitary.getResourceRequirement().getResourceFullDescript());
        return optionsHandler.askUserChoice();

    }

    /**
     * This method is called at the beginning of the game to choose one leader card
     * This method should be non-blocking
     * @param leaderCards the list of resources the player will pay if he chooses this option
     */
    @Override
    public void askLeaderCards(List<LeaderCard> leaderCards){
        LeaderPickerMenu menu = new LeaderPickerMenu(getController(), leaderCards);

        pool.submit(menu);
    }

    /**
     * This method is called at the beginning of the game to choose one personal tile
     * This method should be non-blocking
     *
     * @param standardTile the standard tile, all the player can choose the same tile
     * @param specialTile  the special tile, different for every player
     */
    @Override
    public void askPersonalTiles(PersonalTile standardTile, PersonalTile specialTile) {
        PersonalTilesPickerMenu menu = new PersonalTilesPickerMenu(getController(), standardTile, specialTile);

        pool.submit(menu);
    }

    /**
     * This method is called when a player chooses to play a ledear with a COPY ability and he should be asked to choose
     * This method should be blocking
     * @param possibleLeaders the possibilites to choose from
     * @return the index of the choice
     */
    public int askWhichLeaderAbilityToCopy(List<LeaderCard> possibleLeaders) {
        CliOptionsHandler optionsHandler = new CliOptionsHandler(possibleLeaders.size());
        for(LeaderCard leaderIter : possibleLeaders)
            optionsHandler.addOption(leaderIter.getName() + " - ability: " + leaderIter.getAbility().getAbilityDescription());

        return optionsHandler.askUserChoice();

    }

    /**
     * This method is called when the player it is playing a leader who has a ONCE_PER_ROUND ability
     * to ask the user if he also wants to activate the ability
     * This method should be blocking
     * @return true if he also wants to activate, false otherwise
     */
    public int askAlsoActivateLeaderCard() {
        CliOptionsHandler optionsHandler = new CliOptionsHandler("The leader you chose has a once per round ability\n" +
                "Do you want to activate it now or keep it for later on?", 2);
        optionsHandler.addOption("yes - also activate his once per round effect");
        optionsHandler.addOption("no - do not activate his once per round effect");

        return optionsHandler.askUserChoice();
    }

    /*
     * This method is called when the player performs an action but from the model we have to ask
     * how many servants he wants to add
     * @param minimum the minimum number of servants he shuold at least add (typically 0)
     * @param maximum the maximum number of servants he can add (typically the ones the player has)
     * @return the number of servants the player wants to add to the action
     */
    @Override
    public int askAddingServants(int minimum, int maximum) {
        CliPrinter.println("You can add servants to your current action, you can add from " + minimum
                + " to " + maximum + " servants. How many do you want to add?");

        int choice = StdinSingleton.readAndParseInt();
        while(choice < minimum || choice > maximum) {
            CliPrinter.println("Please add a correct number of servnts, between " + minimum
                    + " and " + maximum);
            choice = StdinSingleton.readAndParseInt();
        }

        return choice;
    }

    /**
     * This method is called when the player activate a leader with a once per round ability that modifies
     * the value of one of his colored family members, he has to choose which one
     * @param availableFamilyMembers the list of available family member, it's useless to modify
     *                               the value of a family member already played
     * @throws IllegalArgumentException if the list is empty
     * @return the color of the family member he chose
     */
    @Override
    public DiceAndFamilyMemberColorEnum askWhichFamilyMemberBonus(List<FamilyMember> availableFamilyMembers) throws IllegalArgumentException {
        if(availableFamilyMembers.isEmpty()) {
            CliPrinter.println("You don't have any family member left to play, it's useless to activate" +
                    "this once per round leader ability right now, please wait next round");
            throw new IllegalArgumentException("the list is empty");
        }

        if(availableFamilyMembers.size() == 1) {
            CliPrinter.println("You can only apply the leader to family member "
                    + availableFamilyMembers.get(0).getColor() +
                    " with value " + availableFamilyMembers.get(0).getValue());
            CliPrinter.println("I'm applying it to this family member");
            return availableFamilyMembers.get(0).getColor();
        } else {
            CliOptionsHandler familyMemberChooser = new CliOptionsHandler(availableFamilyMembers.size());

            for (FamilyMember fmIter : availableFamilyMembers) {
                familyMemberChooser.addOption("Family member of color " + fmIter.getColor() + "of value " + fmIter.getValue());
            }

            int choice = familyMemberChooser.askUserChoice();
            return availableFamilyMembers.get(choice).getColor();
        }
    }

    /**
     * this method is used to start the menu used when the player is waiting the other players playing the phase
     */
    @Override
    public void waitMenu() {

        if(waitMenu.getState() == Thread.State.RUNNABLE || waitMenu.getState() == Thread.State.TIMED_WAITING)
            waitMenu.interrupt();
        waitMenu = new WaitBasicCliMenu(getController());
        waitMenu.start();
        //pool.submit(waitMenu);
    }

    /**
     * this method is called when the game is ended
     * @param playerPositionEndGamePacket the packet with all the information of the end of the game
     */
    @Override
    public void showEndOfGame(List<PlayerPositionEndGamePacket> playerPositionEndGamePacket) {

        CliPrinter.showEndGame(playerPositionEndGamePacket, getController().callbackObtainPlayer().getNickname());
    }

    /**
     * thies message is showed to all the players to inform that a player had passed the turn
     * @param nickname the player that had pass the phase
     */
    @Override
    public void notifyEndOfPhaseOfPlayer(String nickname) {
        CliPrinter.printPlayerGeneralAction(nickname, " has passed the turn.");
    }

    /**
     * This method is used by the controller when it receives a place on tower from another player and wants
     * to notify the user that such a move has happened
     * @param fm the family member used for the move
     * @param towerIndex the index of the tower
     * @param floorIndex the index of the floor AS
     */
    @Override
    public void notifyPlaceOnTower(FamilyMember fm, int towerIndex, int floorIndex) {
        CliPrinter.printFMMoveNotification(fm, "in a tower action space of coordinates [" + towerIndex + ";" +
                floorIndex + "]");
    }

    /**
     * This method is used by the controller when it receives a place on harvest from another player and wants
     * to notify the user that such a move has happened
     * @param fm the family member used for the move
     * @param servantsUsed the number of servants used for the action
     */
    @Override
    public void notifyPlaceOnHarvest(FamilyMember fm, int servantsUsed) {
        CliPrinter.printFMMoveNotification(fm, "in the harvest action space with " + servantsUsed + " servants");
    }

    /**
     * This method is used by the controller when it receives a place on build from another player and wants
     * to notify the user that such a move has happened
     * @param fm the family member used for the move
     * @param servantsUsed the number of servants used for the action
     */
    @Override
    public void notifyPlaceOnBuild(FamilyMember fm, int servantsUsed) {
        CliPrinter.printFMMoveNotification(fm, "in the build action space with " + servantsUsed + " servants");
    }

    /**
     * This method is used by the controller when it receives a place on market from another player and wants
     * to notify the user that such a move has happened
     * @param fm the family member used for the move
     * @param marketIndex the index of the market as
     */
    @Override
    public void notifyPlaceOnMarket(FamilyMember fm, int marketIndex) {
        CliPrinter.printFMMoveNotification(fm, "in a market action space of index " + marketIndex);
    }

    /**
     * This method is used by the controller when it receives a place on the council from another player and wants
     * to notify the user that such a move has happened
     * @param fm the family member used for the move
     */
    @Override
    public void notifyPlaceOnCouncil(FamilyMember fm) {
        CliPrinter.printFMMoveNotification(fm, "in the council");
    }

    /**
     * This method is used by the controller when it receives a discard leader action from another player and wants
     * to notify the user that such a move has happened
     * @param nickname the name of the palyer making the move
     * @param nameCard the name of the leader card involved in the action
     */
    @Override
    public void notifyDiscardLeaderCard(String nickname, String nameCard) {
        CliPrinter.printPlayerGeneralAction(nickname, "has discarded the leader card " + nameCard);
    }

    /**
     * This method is used by the controller when it receives a play leader action from another player and wants
     * to notify the user that such a move has happened
     * @param nickname the name of the palyer making the move
     * @param nameCard the name of the leader card involved in the action
     */
    @Override
    public void notifyPlayLeaderCard(String nickname, String nameCard) {
        CliPrinter.printPlayerGeneralAction(nickname, "has played the leader card " + nameCard);
    }

    /**
     * This method is used by the controller when it receives a activate leader action from another player and wants
     * to notify the user that such a move has happened
     * @param nickname the name of the palyer making the move
     * @param nameCard the name of the leader card involved in the action
     */
    @Override
    public void notifyActivateLeaderCard(String nickname, String nameCard) {
        CliPrinter.printPlayerGeneralAction(nickname, "has activated the leader card " + nameCard);
    }

    /**
     * this method is called by the client to show the player excommunicated on the cli
     * @param playersExcommunicated the nickname of the players excommunicated
     */
    @Override
    public void displayExcommunicationPlayers(List<String> playersExcommunicated) {

        CliPrinter.diplayExcommunication(playersExcommunicated);
    }

    /**
     * this method is called by the client to ask the client if he wants to be excommunicated on the cli
     */
    @Override
    public void askExcommunicationChoice(int numTile) {

        if(waitMenu.getState() == Thread.State.RUNNABLE || waitMenu.getState() == Thread.State.TIMED_WAITING)
            waitMenu.interrupt();

        ExcommunicationMenu menu = new ExcommunicationMenu(getController(),numTile);

        pool.submit(menu);
    }

    /**
     * This method is called by controller to signal that another player was suspende due to timeout
     * @param nickname the nick of the player suspended
     */
    @Override
    public void notifyAnotherPlayerSuspended(String nickname) {
        System.out.println("["+nickname+"] --> " + nickname + " has been suspended, if he doesn't reconnect all " +
                "his phases will be automatically passed");
    }

    /**
     * This method is called by controller to signal that another player was disconnected due to network problems
     * @param nickname the nick of the player disconnected
     */
    @Override
    public void notifyAnotherPlayerDisconnected(String nickname) {
        System.out.println("["+nickname+"] --> " + nickname + " disconnected due to network problems, all " +
                "his phases will be automatically passed till the end of the game");
    }

    /**
     * This method is called by controller to signal that another player has reconnected after suspension
     * @param nickname the nick of the player reconnected
     */
    @Override
    public  void notifyPlayerReconnected(String nickname) {
        System.out.println("["+nickname+"] --> " + nickname + " was suspended, now he reconnected");
    }

    /**
     * Tells the view to remove a card
     *
     * @param towerWrapper contains the coordinates of the card
     */
    @Override
    public void removeCard(TowerWrapper towerWrapper) {
        //here is handled differently from the gui
    }

    /**
     * This method is called by controller to signal that this player was suspended due to timeout
     */
    @Override
    public void notifyThisPlayerSuspended() {
        System.out.println("****ATTENTION**** you didn't play for too long, you have been suspended, " +
                "if you don't do anything the server will automatically pass for you," +
                "to reconnect please write CONNECT");
    }
}

