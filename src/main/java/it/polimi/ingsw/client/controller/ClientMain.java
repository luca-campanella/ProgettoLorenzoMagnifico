package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.client.exceptions.ClientConnectionException;
import it.polimi.ingsw.client.exceptions.LoginException;
import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.client.exceptions.UsernameAlreadyInUseException;
import it.polimi.ingsw.client.network.AbstractClientType;
import it.polimi.ingsw.client.network.NetworkTypeEnum;
import it.polimi.ingsw.client.network.rmi.RMIClient;
import it.polimi.ingsw.client.network.socket.SocketClient;
import it.polimi.ingsw.model.cards.BuildingCard;
import it.polimi.ingsw.model.controller.ModelController;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.NoEffect;
import it.polimi.ingsw.model.effects.immediateEffects.PayForSomethingEffect;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceCollector;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * TODO: implement launcher
 */
public class ClientMain implements ControllerModelInterface, ChoicesHandlerInterface {
    LauncherClientFake temp;
    AbstractUIType userInterface;
    AbstractClientType clientNetwork;
    ModelController modelController;

    /**
     * this hashmap is filled with all the choices the user made regarding the move he's currently performed
     * it is filled by all the callback methods
     * it should be re-instantiated every time a new action is performed
     */
    HashMap<String, Integer> choicesOnCurrentAction;

    /**
     * this resource collector is used to check that the user has sufficient resources to make a build choice
     * it is initialized with the resources of the player at the beginning of the build action
     */
    ResourceCollector resourcesCheckMap;

    FamilyMember familyMemberCurrentAction;

    /**
    this is Class Constructor
     */
    private ClientMain()
    {
        temp = new LauncherClientFake(this);
        userInterface = temp.welcome();
        userInterface.askNetworkType();
        //Questo non penso vada bene in quanto credo debba essere il metodo corrispondente ad istanziare la classe corrispondente --Arto

    }
    public static void main(String args[]) {
        Debug.instance(Debug.LEVEL_VERBOSE);

        new ClientMain();
    }
    /*
    This method returns Client's userInterface
     */
    public AbstractUIType getUserInterface() {
        return userInterface;
    }
    /**
    * This method show user's network choice
     */
    public void callbackNetworkType(NetworkTypeEnum networkChoice){
        Debug.printDebug("I'm in ClientMain.callbackNetworkType, choice = " + networkChoice);
        if(networkChoice == NetworkTypeEnum.RMI) {
            clientNetwork = new RMIClient(this, "127.0.0.1", 3034);
            try {
                clientNetwork.connect();
            } catch (ClientConnectionException e) {
                e.printStackTrace();
                //TODO: handling no Connection
            }
        }
        else {//Here enters if network type is a socket
            clientNetwork = new SocketClient(this, "127.0.0.1", 3035);
            try {
                clientNetwork.connect();
            } catch (ClientConnectionException e) {
                e.printStackTrace();
                //TODO: handling no Connection
            }
        }
        userInterface.askLoginOrCreate();
    }
    /**
    * this method is called when a user is trying to login.
     */
    public void callbackLogin(String userID, String userPW){
        Debug.printDebug("Sono nel ClientMain.callbackLogin.");
        try {
            clientNetwork.loginPlayer(userID, userPW);
        } catch (NetworkException e) {
            //TODO handle network problems
            e.printStackTrace();
        }
        catch (LoginException e) {
            //TODO handle login problems (call the UI again)
            Debug.printDebug("Login exception occurred", e);
            switch(e.getErrorType()) {
                case ALREADY_LOGGED_TO_ROOM :
                    userInterface.printError("Already logged to room");
                    break;
                case NOT_EXISTING_USERNAME:
                    userInterface.printError("The username you inserted doesn't exists");
                    userInterface.askLoginOrCreate();
                break;
                case WRONG_PASSWORD:
                    userInterface.printError("The password you inserted was wrong");
                    userInterface.askLoginOrCreate();
                    break;
                default:
                    userInterface.printError("Something went wrong.");
                    userInterface.askLoginOrCreate();
                    break;

            }

        }
        Debug.printVerbose("Im going to call askChatMsg");
        userInterface.askChatMsg(); //TODO this is a method just for testing chat
    }
    public void callbackLoginAsGuest(){
        //devo settare il nome del player come Guest + ID
        userInterface.readAction();
    }
    public void callbackCreateAccount(String userID, String userPW){
        Debug.printDebug("I'm in ClientMain.callbackCreateAccount");
        try {
            clientNetwork.registerPlayer(userID, userPW);
        } catch (NetworkException e) {
            //TODO handle network problems
            e.printStackTrace();
        }
        catch(UsernameAlreadyInUseException e)
        {
            Debug.printDebug(e);
            userInterface.printError("The username you inserted is already in use, please insert a new one");
            userInterface.askLoginOrCreate();
        }
        userInterface.askLoginOrCreate();
    }

    /**
     * this method it's a callback method that is called from the AbstractyUIType when i want to play a Leader
     */
    public void callbackPlayLeader(){
        initialActionsOnPlayerMove();
        Debug.printDebug("I'm in ClientMain.callbackPlayLeader");
    }

    /**
     * this method it's a callback method called from AbstractUIType when i want to discard a Leader.
     */
    public void callbackDiscardLeader(){
        Debug.printDebug("I'm in ClientMain.callbackDiscardLeader");
    }

    /**
     * this method is a callback method called from abstractUIType when a placement of a family member is performed
     */
    public void callbackPerformPlacement(){
        Debug.printDebug("I'm in ClientMain.callbackPerformPlacement");
        //get status... ricevo una lista di family member che posso usare
        userInterface.selectFamilyMember();
    }

    /**
     * this method is a callback method called from abstractUiType when a family member is selected
     * @param color refers to the color of the family member selected.
     */
    public void callbackFamilyMemberSelected(String color)
    {
        Debug.printDebug("Sono nel ClientMain.callbackFamilyMember");
        //chiamo il server e gli dico che voglio usare quel family member.
        //il server mi dice quali azioni posso fare
        //chiamerò quindi il mio abstract UIType con un qualcosa riguardante...
        userInterface.printAllowedActions();
    }

    /**
     * this method should be called every time a new action / move is perfomed by the user
     * It initialises the data structures used afterwards
     */
    private void initialActionsOnPlayerMove() {
        choicesOnCurrentAction = new HashMap<>();

    }

    /**
     * this method will ask to the model Controller what action user can do
     */
    public void askAction(){
        userInterface.readAction();
    }

    /**
     * This method is called by AbstractClientType to display an incoming chat message (Direction: AbstractClientType -> ClientMain; general direction: Server -> Client)
     * @param senderNick
     * @param msg
     */
    public void receiveChatMsg(String senderNick, String msg) {
        userInterface.displayChatMsg(senderNick, msg);
    }


    /**
     * this is the call back method to send a message to all other players in the room (Direction: {@link AbstractUIType} -> {@link ClientMain}; general direction: Client -> server)
     * @param msg
     * @throws NetworkException
     */
    public void callbackSendChatMsg(String msg) throws NetworkException {
        clientNetwork.sendChatMsg(msg);
    }

    /**
     * Questo metodo che fa? Non c'è nessuno che lo sa -- Arto todo: comment this method
     * @param nameCard
     * @param choices
     * @param resourcePlayer
     * @return
     */
    public int choose(String nameCard, ArrayList<String> choices, HashMap<ResourceTypeEnum, Integer> resourcePlayer){

        return userInterface.askChoice(nameCard, choices, resourcePlayer);

    }

    /**
     * this method allows player to place a family member on a build action space
     * @param familyMember
     * @param servants
     */
    public void callbackPlacedFMOnBuild(FamilyMember familyMember, Resource servants){
        /*We make a copy of the hashmap beacuse we have to perfom some checks on it and this checks should not affect
        the hashmap of the player. Even tough making a copy using the constructor makes just a shallow copy, this is sufficient
        since Integer types are immutable
         */

        resourcesCheckMap = new ResourceCollector(familyMember.getPlayer().getResourcesCollector());

        /*LinkedList<BuildingCard> buildingCards = modelController.getYellowBuildingCards(familyMember.getPlayer());
        ArrayList<ImmediateEffectInterface> effects;

        for(BuildingCard cardIter : buildingCards) {
            effects = cardIter.getEffectsOnBuilding();
            if(effects.size() > 1) {

            }

        }*/


    }

    /**
     * Callback from model to controller
     * The model uses this method when encounters a council gift and should choose between the possible ones
     * The method also performs checks that all the chosen council gifts are different one another
     * This implementation calls the view and asks what the user wants to choose
     * The UI should perform a <b>blocking</b> question to the user and return directly to this method
     * @param choiceCode
     * @param numberDiffGifts the number of different council gifts to ask for
     * @return The arraylist of effect chosen
     */
    @Override
    public ArrayList<GainResourceEffect> callbackOnCoucilGift(String choiceCode, int numberDiffGifts) {
        ArrayList<GainResourceEffect> options = modelController.getBoard().getCouncilAS().getCouncilGiftChoices();
        ArrayList<GainResourceEffect> choices = new ArrayList<>(numberDiffGifts);
        int choice;

        for(int i = 0; i < numberDiffGifts; i++) {
            choice = userInterface.askCouncilGift(options);
            choices.add(options.get(choice));
            options.remove(choice);
            choicesOnCurrentAction.put(choiceCode + i, choice);
        }

        return choices;
    }

    /**
     * Callback from model to controller
     * The model uses this method when encounters a {@link BuildingCard} with more than one effects and wants to make the user choose which one activate
     * This callback should be called even if the user has no choices, because it also performs resource checks
     * If in these chacks it understands no effect can be chosen then it returns a {@link NoEffect} class and puts the choice in the hashmap to -1
     * This implementation calls the view and asks what the user wants to choose
     * The UI should perform a <b>blocking</b> question to the user and return directly to this method
     * @param cardNameChoiceCode
     * @param possibleEffectChoices
     * @return
     */
    @Override
    public ImmediateEffectInterface callbackOnYellowBuildingCardEffectChoice(String cardNameChoiceCode, ArrayList<ImmediateEffectInterface> possibleEffectChoices) {

        //We will make a copy of the arraylist beacuse we have to remove some objects that cannot be chosen from the user
        ArrayList<ImmediateEffectInterface> realPossibleEffectChoices = new ArrayList<>(possibleEffectChoices.size());
        ImmediateEffectInterface effectIter;
        //we check if the user has left sufficient resources to perform this effect
        for(int i = 0; i < possibleEffectChoices.size(); i++) {
            effectIter = possibleEffectChoices.get(i);
            if(effectIter instanceof PayForSomethingEffect) {
                if(!resourcesCheckMap.checkIfContainable(((PayForSomethingEffect) effectIter).getToPay()));
                    continue; //we should not add the option because the player doesn't have enough resources
            }
            realPossibleEffectChoices.add(effectIter);
        }

        int choice;
        ImmediateEffectInterface effectChosen;

        //if there's no possibility left or one possibility the choice is already made, no need to ask the user
        if(possibleEffectChoices.size() == 0)
        {
            choice = -1;
            effectChosen = new NoEffect();
        }
        else if(possibleEffectChoices.size() == 1) {
            effectChosen = realPossibleEffectChoices.get(1);
            choice = possibleEffectChoices.indexOf(effectChosen);
        }
        else { //there are possible choices: let's ask the UI what to chose
            int tmpChoice = userInterface.askYellowBuildingCardEffectChoice(possibleEffectChoices);
            effectChosen = realPossibleEffectChoices.get(tmpChoice);
            choice = possibleEffectChoices.indexOf(effectChosen);
        }
        //TODO implement choose no choice
        //we need to subtract the resources he payed from the copy of the hashmap in order to be sure next checks are correct
        if(effectChosen instanceof PayForSomethingEffect) {
            resourcesCheckMap.addResources(((PayForSomethingEffect) effectChosen).getToGain());
        }

        choicesOnCurrentAction.put(cardNameChoiceCode, choice);
        return effectChosen;
    }


    /**
     * This method checks if the user has sufficient resources to play for that effect, mostly used for {@link PayForSomethingEffect} checks
     * @param resMap The hashmap of the resources of the player
     * @param resToPay the arraylist of the resources he has to pay
     * @return if he can pay them or not
     */
    private boolean checkIfPayable(HashMap<ResourceTypeEnum, Integer> resMap, ArrayList<Resource> resToPay) {
        for(Resource resIter : resToPay) {
            if(resMap.get(resIter.getType()) < resIter.getValue())
                return false;
        }

        return true;
    }

    /**
     * this method is used to add an array of resources to an hasmap of resources
     * @param resources the object of the resource, it contains the value and the type
     */
    private void addResourcesToMap(HashMap<ResourceTypeEnum, Integer> resMap, ArrayList<Resource> resources){

        for(Resource resource : resources){
            resMap.put(resource.getType(),resMap.get(resource.getType())+resource.getValue());
        }

    }
}


