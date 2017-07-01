package it.polimi.ingsw.model.board;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by higla on 01/07/2017.
 */
public class VaticanReportTest {
    VaticanReport vaticanReport = new VaticanReport();
    @Test
    public void setRequiredFaithPoints() throws Exception {
        int[] init = {2,2};
        vaticanReport.setRequiredFaithPoints(init);
        for(int i = 0; i<init.length; i++)
            assertEquals(2,init[i]);

    }

    @Test
    public void setCorrespondingVictoryPoints() throws Exception {
        int[] init = {2,2};
        vaticanReport.setCorrespondingVictoryPoints(init);
        for(int i = 0; i<init.length; i++)
            assertEquals(2,init[i]);

    }

}