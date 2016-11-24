/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.nestedtargetproperties;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.nestedsourceproperties._target.ChartEntry;
import org.mapstruct.ap.test.nestedsourceproperties.source.Chart;
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
        @Mapping(target = "song.title", source = "songTitle" ),
        @Mapping(target = "song.artist.name", source = "artistName" ),
        @Mapping(target = "song.artist.label.studio.name", source = "recordedAt"),
        @Mapping(target = "song.artist.label.studio.city", source = "city" ),
        @Mapping(target = "song.positions", source = "position" )
    })
    public abstract Chart map(ChartEntry chartEntry);

    @Mappings({
        @Mapping(target = "type", ignore = true),
        @Mapping(target = "name", source = "chartEntry2.chartName"),
        @Mapping(target = "song.title", source = "chartEntry1.songTitle" ),
        @Mapping(target = "song.artist.name", source = "chartEntry1.artistName" ),
        @Mapping(target = "song.artist.label.studio.name", source = "chartEntry1.recordedAt"),
        @Mapping(target = "song.artist.label.studio.city", source = "chartEntry1.city" ),
        @Mapping(target = "song.positions", source = "chartEntry2.position" )
    })
    public abstract Chart map(ChartEntry chartEntry1, ChartEntry chartEntry2);

    @InheritInverseConfiguration
    public abstract ChartEntry map(Chart chart);

    protected List<Integer> mapPosition(Integer in) {
        if ( in != null ) {
            return Arrays.asList( in );
        }
        else {
            return Collections.<Integer>emptyList();
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
