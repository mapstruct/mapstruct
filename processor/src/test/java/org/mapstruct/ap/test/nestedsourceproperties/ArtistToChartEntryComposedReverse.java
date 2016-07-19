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

    @Mappings({
        @Mapping(target = "songTitle", source = "title"),
        @Mapping(target = "artistName", source = "artist.name"),
        @Mapping(target = "label", source = "artist.label"),
        @Mapping(target = "position", ignore = true),
        @Mapping(target = "chartName", ignore = true )
    })
    abstract ChartEntryComposed mapForward(Song song);

    @Mappings({
        @Mapping(target = "name", source = "name"),
        @Mapping(target = "recordedAt", source = "studio.name"),
        @Mapping(target = "city", source = "studio.city")
    })
    abstract ChartEntryLabel mapForward(Label label);

    @InheritInverseConfiguration
    @Mapping(target = "positions", ignore = true)
    abstract Song mapReverse(ChartEntryComposed ce);

    @InheritInverseConfiguration
    abstract Label mapReverse(ChartEntryLabel label);
}
