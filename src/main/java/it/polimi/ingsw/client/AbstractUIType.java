package it.polimi.ingsw.client;

/**
 * Created by higla on 11/05/2017.
 */
//TODO classe astratta che mi rende trasparente l'uso di CLI / GUI

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

}

