/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
