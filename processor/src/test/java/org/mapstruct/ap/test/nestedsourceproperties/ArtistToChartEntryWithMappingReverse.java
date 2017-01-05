/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
import org.mapstruct.ap.test.nestedsourceproperties._target.ChartEntryWithMapping;
import org.mapstruct.ap.test.nestedsourceproperties.source.Song;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public abstract class ArtistToChartEntryWithMappingReverse {

    public static final ArtistToChartEntryWithMappingReverse MAPPER =
        Mappers.getMapper( ArtistToChartEntryWithMappingReverse.class );

    @Mappings({
        @Mapping(target = "songTitle", source = "title"),
        @Mapping(target = "artistId", source = "artist.name"),
        @Mapping(target = "recordedAt", source = "artist.label.studio.name"),
        @Mapping(target = "city", source = "artist.label.studio.city"),
        @Mapping(target = "position", ignore = true),
        @Mapping(target = "chartName", ignore = true )
    })
    abstract ChartEntryWithMapping mapForward(Song song);

    @InheritInverseConfiguration
    @Mapping(target = "positions", ignore = true)
    abstract Song mapReverse(ChartEntryWithMapping ce);

    int mapArtistToArtistId(String in) {

        if ( "The Beatles".equals( in ) ) {
            return 1;
        }
        else {
            return -1;
        }
    }

    String mapArtistIdToArtist(int in) {

        if (  in == 1 ) {
            return "The Beatles";
        }
        else {
            return "Unknown";
        }
    }

}
