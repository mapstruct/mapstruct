/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.nestedsourceproperties;

import org.mapstruct.InheritInverseConfiguration;
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

    @Mappings({
        @Mapping(target = "chartName", source = "chart.name"),
        @Mapping(target = "songTitle", source = "song.title"),
        @Mapping(target = "artistName", source = "song.artist.name"),
        @Mapping(target = "recordedAt", source = "song.artist.label.studio.name"),
        @Mapping(target = "city", source = "song.artist.label.studio.city"),
        @Mapping(target = "position", source = "position")
    })
    ChartEntry map(Chart chart, Song song, Integer position);

    @Mappings({
        @Mapping(target = "chartName", ignore = true),
        @Mapping(target = "songTitle", source = "title"),
        @Mapping(target = "artistName", source = "artist.name"),
        @Mapping(target = "recordedAt", source = "artist.label.studio.name"),
        @Mapping(target = "city", source = "artist.label.studio.city"),
        @Mapping(target = "position", ignore = true)
    })
    ChartEntry map(Song song);

    @InheritInverseConfiguration
    @Mappings({
        @Mapping(target = "artist", ignore = true),
        @Mapping(target = "positions", ignore = true)
    })
    Song map(ChartEntry song);


    @Mappings({
        @Mapping(target = "chartName", source = "name"),
        @Mapping(target = "songTitle", ignore = true),
        @Mapping(target = "artistName", ignore = true),
        @Mapping(target = "recordedAt", ignore = true),
        @Mapping(target = "city", ignore = true),
        @Mapping(target = "position", ignore = true)
    })
    ChartEntry map(Chart name);

}
