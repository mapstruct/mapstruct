/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.nestedsource;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.constructor.nestedsource._target.ChartEntryWithMapping;
import org.mapstruct.ap.test.constructor.nestedsource.source.Song;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public abstract class ArtistToChartEntryWithMappingReverse {

    public static final ArtistToChartEntryWithMappingReverse MAPPER =
        Mappers.getMapper( ArtistToChartEntryWithMappingReverse.class );

    @Mappings({
        @Mapping(target = "songTitle", source = "title"),
        @Mapping(target = "artistId", source = "artist.name"),
        @Mapping(target = "recordedAt", source = "artist.label.studio.name"),
        @Mapping(target = "city", source = "artist.label.studio.city"),
        @Mapping(target = "position", ignore = true),
        @Mapping(target = "chartName", ignore = true)
    })
    abstract ChartEntryWithMapping mapForward(Song song);

    @InheritInverseConfiguration
    @Mapping(target = "positions", ignore = true)
    abstract Song mapReverse(ChartEntryWithMapping ce);

    int mapArtistToArtistId(String in) {

        if ( "The Beatles".equals( in ) ) {
            return 1;
        }
        else {
            return -1;
        }
    }

    String mapArtistIdToArtist(int in) {

        if ( in == 1 ) {
            return "The Beatles";
        }
        else {
            return "Unknown";
        }
    }

}
