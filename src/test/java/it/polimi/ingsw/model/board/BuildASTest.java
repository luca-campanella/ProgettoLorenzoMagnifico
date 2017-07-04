package it.polimi.ingsw.model.board;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class BuildASTest {


    @Test
    public void setTwoPlayersOneSpace() throws Exception {
        BuildAS build = new BuildAS(0,3);
        build.setValueMalus(4);
        assertEquals(0, build.getValueNeeded(true));
        assertEquals(4, build.getValueMalus());
        build.setTwoPlayersOneSpace(true);
        assertEquals(true, build.isTwoPlayersOneSpace());


    }

}