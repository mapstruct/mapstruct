/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.presencecheck.spi;

import java.util.List;

/**
 * @author Cindy Wang
 */
public class SoccerTeamTargetWithPresenceCheck {

    private List<String> players;
    private String goalKeeperName;

    private boolean hasPlayers = false;
    private boolean hasGoalKeeperName = false;

    public List<String> getPlayers() {
        return players;
    }

    public String getGoalKeeperName() {
        return goalKeeperName;
    }

    public void setGoalKeeperName(String goalKeeperName) {
        this.goalKeeperName = goalKeeperName;
        hasGoalKeeperName = true;
    }

    public boolean hasPlayers() {
        return hasPlayers;
    }

    public boolean hasGoalKeeperName() {
        return hasGoalKeeperName;
    }
}
