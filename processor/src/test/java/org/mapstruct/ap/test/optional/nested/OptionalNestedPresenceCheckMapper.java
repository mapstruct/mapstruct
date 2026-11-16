/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.nested;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface OptionalNestedPresenceCheckMapper {

    @Mapping(target = "songTitle", source = "song.title")
    @Mapping(target = "artistName", source = "song.artist.name")
    @Mapping(target = "recordedAt", source = "song.artist.label.studio.name")
    @Mapping(target = "city", source = "song.artist.label.studio.city")
    TargetAggregate map(Source song);

    class Source {

        private final String title;
        private final Artist artist;

        public Source(String title, Artist artist) {
            this.title = title;
            this.artist = artist;
        }

        public String getTitle() {
            return title;
        }

        public Optional<Artist> getArtist() {
            return Optional.ofNullable( artist );
        }
    }

}
