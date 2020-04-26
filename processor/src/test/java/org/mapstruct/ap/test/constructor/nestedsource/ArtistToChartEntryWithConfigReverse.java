/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.nestedsource;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.constructor.nestedsource._target.ChartEntryWithBase;
import org.mapstruct.ap.test.constructor.nestedsource.source.Song;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper( config = ArtistToChartEntryConfig.class )
public abstract class ArtistToChartEntryWithConfigReverse {

    public static final ArtistToChartEntryWithConfigReverse MAPPER =
        Mappers.getMapper( ArtistToChartEntryWithConfigReverse.class );

    @InheritConfiguration
    @Mappings({
        @Mapping(target = "recordedAt", source = "artist.label.studio.name"),
        @Mapping(target = "city", source = "artist.label.studio.city"),
        @Mapping(target = "position", ignore = true)
    })
    abstract ChartEntryWithBase mapForward(Song song);

    @InheritInverseConfiguration( name = "mapForward" )
    @Mapping(target = "positions", ignore = true)
    abstract Song mapReverse(ChartEntryWithBase ce);
}
