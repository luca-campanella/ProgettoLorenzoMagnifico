package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.model.player.FamilyMember;

import java.util.ArrayList;

/**
 * the interface of ModelController on the client
 */
public interface ControllerModelInterface {

    public int choose(String nameCard, ArrayList<String> choices);

}
