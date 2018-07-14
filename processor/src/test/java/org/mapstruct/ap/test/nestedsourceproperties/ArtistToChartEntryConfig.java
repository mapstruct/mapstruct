/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedsourceproperties;

import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ap.test.nestedsourceproperties._target.BaseChartEntry;
import org.mapstruct.ap.test.nestedsourceproperties.source.Song;

/**
 *
 * @author Sjaak Derksen
 */
@MapperConfig( unmappedTargetPolicy = ReportingPolicy.ERROR )
public interface ArtistToChartEntryConfig {

    @Mappings({
        @Mapping(target = "songTitle", source = "title"),
        @Mapping(target = "artistName", source = "artist.name"),
        @Mapping(target = "chartName", ignore = true )
    })
    BaseChartEntry mapForwardConfig( Song song );
}
