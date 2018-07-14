/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.presencecheck.spi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sjaak Derksen
 */
public class SoccerTeamTarget {

    private List<String> players;
    private String goalKeeperName;

    public List<String> getPlayers() {
        return players;
    }

    public void addPlayer(String player) {
        if ( this.players == null ) {
            this.players = new ArrayList<String>();
        }
        this.players.add( player );
    }

    public String getGoalKeeperName() {
        return goalKeeperName;
    }

    public void setGoalKeeperName(String goalKeeperName) {
        this.goalKeeperName = goalKeeperName;
    }

}
