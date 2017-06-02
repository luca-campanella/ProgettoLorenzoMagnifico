package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is the interface of ModelController on the client
 */
public interface ControllerModelInterface {

    public int choose(String nameCard, ArrayList<String> choices, HashMap<ResourceTypeEnum, Integer> resourcePlayer);

}
