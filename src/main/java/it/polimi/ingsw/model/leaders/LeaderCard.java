package it.polimi.ingsw.model.leaders;

import it.polimi.ingsw.model.leaders.leadersabilities.AbstractLeaderAbility;
import it.polimi.ingsw.model.leaders.leadersabilities.PermanenteLeaderAbility.AbstractPermanentLeaderAbility;
import it.polimi.ingsw.model.leaders.requirements.AbstractRequirement;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is the Abstract class for leaders
 */
public class LeaderCard implements Serializable {
    private ArrayList<AbstractRequirement> requirements;
    private String name;
    private String description;
    private AbstractLeaderAbility ability;
    //private boolean hasOncePerRoundAbility;

    /**
     * This constructor should be called when you can already set all parameters
     * @param requirements ArrayList of {@link AbstractRequirement}
     * @param name The name of the leader
     * @param description the description of the leader
     * @param ability the the ability of the leader
     */
    public LeaderCard(ArrayList<AbstractRequirement> requirements, String name, String description, AbstractLeaderAbility ability) {
        this.requirements = requirements;
        this.name = name;
        this.description = description;
        this.ability = ability;
    }

    /**
     * This constructor should be called when you want to set the requirements afterwards
     * @param name The name of the leader
     * @param description the description of the leader
     * @param ability the ability of the leader
     */
    public LeaderCard(String name, String description, AbstractLeaderAbility ability) {
        this.name = name;
        this.description = description;
        this.ability = ability;
        requirements = new ArrayList<AbstractRequirement>(1);
    }

    /**
     * Method to add a requirement to the list of requirements
     * @param req the requirement to be added
     */
    protected void addRequirement(AbstractRequirement req) {
        requirements.add(req);
    }

    public ArrayList<AbstractRequirement> getRequirements() {
        return requirements;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public AbstractLeaderAbility getAbility() {
        return ability;
    }
}
