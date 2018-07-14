/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedsourceproperties;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.nestedsourceproperties._target.ChartEntry;
import org.mapstruct.ap.test.nestedsourceproperties.source.Song;
import org.mapstruct.ap.test.nestedsourceproperties.source.SourceDtoFactory;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper( uses = { SourceDtoFactory.class } )
public abstract class ArtistToChartEntryWithFactoryReverse {

    public static final ArtistToChartEntryWithFactoryReverse MAPPER
        = Mappers.getMapper( ArtistToChartEntryWithFactoryReverse.class );

    @Mappings({

        @Mapping(target = "songTitle", source = "title"),
        @Mapping(target = "artistName", source = "artist.name"),
        @Mapping(target = "recordedAt", source = "artist.label.studio.name"),
        @Mapping(target = "city", source = "artist.label.studio.city"),
        @Mapping(target = "position", ignore = true),
        @Mapping(target = "chartName", ignore = true )
    })
    abstract ChartEntry mapForward(Song song);

    @InheritInverseConfiguration
    @Mapping(target = "positions", ignore = true)
    abstract Song mapReverse(ChartEntry ce);
}
