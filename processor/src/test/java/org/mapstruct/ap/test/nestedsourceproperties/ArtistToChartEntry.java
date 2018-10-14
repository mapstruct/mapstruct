/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedsourceproperties;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.nestedsourceproperties._target.ChartEntry;
import org.mapstruct.ap.test.nestedsourceproperties.source.Chart;
import org.mapstruct.ap.test.nestedsourceproperties.source.Song;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface ArtistToChartEntry {

    ArtistToChartEntry MAPPER = Mappers.getMapper( ArtistToChartEntry.class );

    @Mapping(target = "chartName", source = "chart.name")
    @Mapping(target = "songTitle", source = "song.title")
    @Mapping(target = "artistName", source = "song.artist.name")
    @Mapping(target = "recordedAt", source = "song.artist.label.studio.name")
    @Mapping(target = "city", source = "song.artist.label.studio.city")
    @Mapping(target = "position", source = "position")
    ChartEntry map(Chart chart, Song song, Integer position);

    @Mapping(target = "chartName", ignore = true)
    @Mapping(target = "songTitle", source = "title")
    @Mapping(target = "artistName", source = "artist.name")
    @Mapping(target = "recordedAt", source = "artist.label.studio.name")
    @Mapping(target = "city", source = "artist.label.studio.city")
    @Mapping(target = "position", ignore = true)
    ChartEntry map(Song song);

    @Mapping(target = "chartName", source = "name")
    @Mapping(target = "songTitle", ignore = true)
    @Mapping(target = "artistName", ignore = true)
    @Mapping(target = "recordedAt", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "position", ignore = true)
    ChartEntry map(Chart name);

}
