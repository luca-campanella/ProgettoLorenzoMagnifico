package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.UIControllerUserInterface;

import java.util.ArrayList;

/**
 * Created by higla on 11/05/2017.
 */
//TODO classe astratta che mi rende trasparente l'uso di CLI / gui

abstract public class AbstractUIType {
    UIControllerUserInterface UIController = new UIControllerUserInterface();

    //inizia a chiedere all'utente di fare azioni
    abstract public void readAction();
    //chiede all'utente con quale metodo si vuole connettere
    abstract public void askNetworkType();
    //chiede all'utente se ha già un account e loggare o se vuole crearne uno nuovo
    abstract public void askLoginOrCreate();
    //permette all'utente di create un nuovo account
    abstract public void createNewAccount();
    //permette all'utente di Loggare
    abstract public void askLogin();
    //aggiorna l'UI
    abstract public void updateView();
    //Highlights that login has failed and manages that
    abstract public void loginFailure(String failureReason);
    //Selects a family member
    abstract public void selectFamilyMember();
    //stampa le azioni disp
    abstract public void printAllowedActions();

    /**
     * this method just alerts user that there was an error somewhere. It doesn't handle the error
     */
    abstract public void printError(String error);

    /**
     * This method is called by {@link ClientMain} to display an incoming chat message (Direction: {@link ClientMain} -> {@link AbstractUIType}; general direction: Server -> Client)
     * @param senderNick
     * @param msg
     */
    public abstract void displayChatMsg(String senderNick, String msg);

    //TODO this is a method just for testing chat
    public abstract void askChatMsg();


    public abstract int askChoice(String nameCard, ArrayList<String> choices);
}

