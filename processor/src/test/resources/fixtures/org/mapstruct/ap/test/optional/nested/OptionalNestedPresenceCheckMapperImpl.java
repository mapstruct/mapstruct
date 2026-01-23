/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.nested;

import java.util.Optional;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-23T09:37:23+0100",
    comments = "version: , compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
public class OptionalNestedPresenceCheckMapperImpl implements OptionalNestedPresenceCheckMapper {

    @Override
    public TargetAggregate map(Source song) {
        if ( song == null ) {
            return null;
        }

        TargetAggregate targetAggregate = new TargetAggregate();

        targetAggregate.setSongTitle( song.getTitle() );
        targetAggregate.setArtistName( songArtistName( song ) );
        targetAggregate.setRecordedAt( songArtistLabelStudioName( song ) );
        Optional<String> city = songArtistLabelStudioCity( song );
        if ( city.isPresent() ) {
            targetAggregate.setCity( city.get() );
        }

        return targetAggregate;
    }

    private String songArtistName(Source source) {
        Optional<Artist> artist = source.getArtist();
        if ( artist.isEmpty() ) {
            return null;
        }
        Artist artistValue = artist.get();
        if ( !artistValue.hasName() ) {
            return null;
        }
        return artistValue.getName();
    }

    private String songArtistLabelStudioName(Source source) {
        Optional<Artist> artist = source.getArtist();
        if ( artist.isEmpty() ) {
            return null;
        }
        Artist artistValue = artist.get();
        if ( !artistValue.hasLabel() ) {
            return null;
        }
        Optional<Artist.Label> label = artistValue.getLabel();
        if ( label.isEmpty() ) {
            return null;
        }
        Artist.Label labelValue = label.get();
        if ( !labelValue.hasStudio() ) {
            return null;
        }
        Optional<Artist.Studio> studio = labelValue.getStudio();
        if ( studio.isEmpty() ) {
            return null;
        }
        return studio.get().getName();
    }

    private Optional<String> songArtistLabelStudioCity(Source source) {
        Optional<Artist> artist = source.getArtist();
        if ( artist.isEmpty() ) {
            return Optional.empty();
        }
        Artist artistValue = artist.get();
        if ( !artistValue.hasLabel() ) {
            return Optional.empty();
        }
        Optional<Artist.Label> label = artistValue.getLabel();
        if ( label.isEmpty() ) {
            return Optional.empty();
        }
        Artist.Label labelValue = label.get();
        if ( !labelValue.hasStudio() ) {
            return Optional.empty();
        }
        Optional<Artist.Studio> studio = labelValue.getStudio();
        if ( studio.isEmpty() ) {
            return Optional.empty();
        }
        Artist.Studio studioValue = studio.get();
        if ( !studioValue.hasCity() ) {
            return Optional.empty();
        }
        return studioValue.getCity();
    }
}
