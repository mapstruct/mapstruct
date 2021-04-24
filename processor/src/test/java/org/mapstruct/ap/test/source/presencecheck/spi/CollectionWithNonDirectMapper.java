/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.presencecheck.spi;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface CollectionWithNonDirectMapper {

    CollectionWithNonDirectMapper INSTANCE = Mappers.getMapper( CollectionWithNonDirectMapper.class );

    Target map(Source source);

    class Target {

        private Collection<Player> players;

        public Collection<Player> getPlayers() {
            return players;
        }

        public void setPlayers(Collection<Player> players) {
            this.players = players;
        }
    }

    class Player {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class Source {

        private final Collection<PlayerSource> players;

        public Source(Collection<PlayerSource> players) {
            this.players = players;
        }

        public Collection<PlayerSource> getPlayers() {
            return players;
        }

        public boolean hasPlayers() {
            return players != null && !players.isEmpty();
        }
    }

    class PlayerSource {

        private final String name;

        public PlayerSource(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
