/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedsourceproperties;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.nestedsourceproperties._target.ChartPositions;
import org.mapstruct.ap.test.nestedsourceproperties.source.Chart;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ArtistToChartEntryAdder {

    ArtistToChartEntryAdder MAPPER = Mappers.getMapper( ArtistToChartEntryAdder.class );

    @Mappings({
        @Mapping(target = "positions", source = "chart.song.positions"),
    })
    ChartPositions map(Chart chart);
}
