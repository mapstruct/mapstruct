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
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public abstract class ArtistToChartEntryReverse {

    public static final ArtistToChartEntryReverse MAPPER = Mappers.getMapper( ArtistToChartEntryReverse.class );

    @Mapping(target = "songTitle", source = "title")
    @Mapping(target = "artistName", source = "artist.name")
    @Mapping(target = "recordedAt", source = "artist.label.studio.name")
    @Mapping(target = "city", source = "artist.label.studio.city")
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "chartName", ignore = true )
    abstract ChartEntry mapForward(Song song);

    @InheritInverseConfiguration
    @Mapping(target = "positions", ignore = true)
    abstract Song mapReverse(ChartEntry ce);
}
