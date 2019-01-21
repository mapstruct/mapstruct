/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedtargetproperties;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ap.test.nestedsourceproperties._target.ChartEntry;
import org.mapstruct.ap.test.nestedsourceproperties.source.Chart;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper(
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class ChartEntryToArtistUpdate {

    public static final ChartEntryToArtistUpdate MAPPER = Mappers.getMapper( ChartEntryToArtistUpdate.class );

    @Mappings({
        @Mapping(target = "type", ignore = true),
        @Mapping(target = "name", source = "chartName"),
        @Mapping(target = "song.title", source = "songTitle" ),
        @Mapping(target = "song.artist.name", source = "artistName" ),
        @Mapping(target = "song.artist.label.studio.name", source = "recordedAt"),
        @Mapping(target = "song.artist.label.studio.city", source = "city" ),
        @Mapping(target = "song.positions", source = "position" )
    })
    public abstract void map(ChartEntry chartEntry, @MappingTarget Chart chart );

    protected List<Integer> mapPosition(Integer in) {
        if ( in != null ) {
            return Arrays.asList( in );
        }
        else {
            return Collections.<Integer>emptyList();
        }
    }

    protected Integer mapPosition(List<Integer> in) {
        if ( in != null && !in.isEmpty() ) {
            return in.get( 0 );
        }
        else {
            return null;
        }
    }
}
