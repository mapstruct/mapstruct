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
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface ArtistToChartEntryErroneous {

    ArtistToChartEntryErroneous MAPPER = Mappers.getMapper( ArtistToChartEntryErroneous.class );

    @Mapping(target = "chartName", ignore = true)
    @Mapping(target = "songTitle", ignore = true)
    @Mapping(target = "artistName", ignore = true)
    @Mapping(target = "recordedAt", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "position", source = "position")
    ChartEntry forward(Integer position);

    @InheritInverseConfiguration
    Integer reverse(ChartEntry position);

}
