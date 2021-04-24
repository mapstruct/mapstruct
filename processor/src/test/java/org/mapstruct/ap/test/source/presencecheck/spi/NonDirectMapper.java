/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.presencecheck.spi;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface NonDirectMapper {

    NonDirectMapper INSTANCE = Mappers.getMapper( NonDirectMapper.class );

    SoccerTeam map(SoccerTeamSource source);

    class SoccerTeam {

        private Goalkeeper goalKeeper;

        public Goalkeeper getGoalKeeper() {
            return goalKeeper;
        }

        public void setGoalKeeper(Goalkeeper goalKeeper) {
            this.goalKeeper = goalKeeper;
        }
    }

    class Goalkeeper {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class SoccerTeamSource {

        private final GoalKeeperSource goalKeeper;

        public SoccerTeamSource(GoalKeeperSource goalKeeper) {
            this.goalKeeper = goalKeeper;
        }

        public GoalKeeperSource getGoalKeeper() {
            return goalKeeper;
        }

        public boolean hasGoalKeeper() {
            return goalKeeper != null && goalKeeper.getName() != null && !goalKeeper.getName().isEmpty();
        }
    }

    class GoalKeeperSource {

        private final String name;

        public GoalKeeperSource(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
