/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.presencecheck.spi;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CollectionWithNullValueCheckMapper {

    CollectionWithNullValueCheckMapper INSTANCE = Mappers.getMapper( CollectionWithNullValueCheckMapper.class );

    Target map(Source source);

    class Target {

        private Collection<String> players;

        public Collection<String> getPlayers() {
            return players;
        }

        public void setPlayers(Collection<String> players) {
            this.players = players;
        }
    }

    class Source {

        private final Collection<String> players;

        public Source(Collection<String> players) {
            this.players = players;
        }

        public Collection<String> getPlayers() {
            return players;
        }

        public boolean hasPlayers() {
            return players != null && !players.isEmpty();
        }
    }
}
