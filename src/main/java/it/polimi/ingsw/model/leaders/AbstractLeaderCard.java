package it.polimi.ingsw.model.leaders;

import java.util.ArrayList;

/**
 * This class is the Abstract class for leaders
 */
public class AbstractLeaderCard {
    private ArrayList<AbstractRequirement> requirements;
    private String name;
    private String description;
    private String abilityDescription;

    /**
     * This constructor should be called when you can already set all parameters
     * @param requirements ArrayList of {@link AbstractRequirement}
     * @param name The name of the leader
     * @param description the description of the leader
     * @param abilityDescription the description of the ability of the leader
     */
    public AbstractLeaderCard(ArrayList<AbstractRequirement> requirements, String name, String description, String abilityDescription) {
        this.requirements = requirements;
        this.name = name;
        this.description = description;
        this.abilityDescription = abilityDescription;
    }

    /**
     * This constructor should be called when you want to set the requirements afterwards
     * @param name The name of the leader
     * @param description the description of the leader
     * @param abilityDescription the description of the ability of the leader
     */
    public AbstractLeaderCard(String name, String description, String abilityDescription) {
        this.name = name;
        this.description = description;
        this.abilityDescription = abilityDescription;
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

    public String getAbilityDescription() {
        return abilityDescription;
    }
}
