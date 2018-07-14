/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.presencecheck.spi;

import java.util.List;

/**
 * @author Sjaak Derksen
 */
public class SoccerTeamSource {

    private List<String> players;
    private boolean hasPlayers = true;

    private GoalKeeper goalKeeper;
    private boolean hasGoalKeeper = true;

    public boolean hasPlayers() {
        return hasPlayers;
    }

    public void setHasPlayers(boolean has) {
        this.hasPlayers = has;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public GoalKeeper getGoalKeeper() {
        return goalKeeper;
    }

    public void setGoalKeeper(GoalKeeper goalKeeper) {
        this.goalKeeper = goalKeeper;
    }

    public boolean hasGoalKeeper() {
        return hasGoalKeeper;
    }

    public void setHasGoalKeeper(boolean hasGoalKeeper) {
        this.hasGoalKeeper = hasGoalKeeper;
    }

}
