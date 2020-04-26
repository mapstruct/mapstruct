/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.nestedtarget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.constructor.nestedsource._target.ChartEntry;
import org.mapstruct.ap.test.constructor.nestedsource.source.Chart;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public abstract class ChartEntryToArtist {

    public static final ChartEntryToArtist MAPPER = Mappers.getMapper( ChartEntryToArtist.class );

    @Mappings({
        @Mapping(target = "type", ignore = true),
        @Mapping(target = "name", source = "chartName"),
        @Mapping(target = "song.title", source = "songTitle"),
        @Mapping(target = "song.artist.name", source = "artistName"),
        @Mapping(target = "song.artist.label.studio.name", source = "recordedAt"),
        @Mapping(target = "song.artist.label.studio.city", source = "city"),
        @Mapping(target = "song.positions", source = "position")
    })
    public abstract Chart map(ChartEntry chartEntry);

    @InheritInverseConfiguration
    public abstract ChartEntry map(Chart chart);

    protected List<Integer> mapPosition(Integer in) {
        if ( in != null ) {
            return new ArrayList<>( Arrays.asList( in ) );
        }
        else {
            return new ArrayList<>();
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
