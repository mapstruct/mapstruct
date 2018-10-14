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
import org.mapstruct.ap.test.nestedsourceproperties._target.ChartEntryComposed;
import org.mapstruct.ap.test.nestedsourceproperties._target.ChartEntryLabel;
import org.mapstruct.ap.test.nestedsourceproperties.source.Label;
import org.mapstruct.ap.test.nestedsourceproperties.source.Song;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public abstract class ArtistToChartEntryComposedReverse {

    public static final ArtistToChartEntryComposedReverse MAPPER =
        Mappers.getMapper( ArtistToChartEntryComposedReverse.class );

    @Mapping(target = "songTitle", source = "title")
    @Mapping(target = "artistName", source = "artist.name")
    @Mapping(target = "label", source = "artist.label")
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "chartName", ignore = true )
    abstract ChartEntryComposed mapForward(Song song);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "recordedAt", source = "studio.name")
    @Mapping(target = "city", source = "studio.city")
    abstract ChartEntryLabel mapForward(Label label);

    @InheritInverseConfiguration
    @Mapping(target = "positions", ignore = true)
    abstract Song mapReverse(ChartEntryComposed ce);

    @InheritInverseConfiguration
    abstract Label mapReverse(ChartEntryLabel label);
}
