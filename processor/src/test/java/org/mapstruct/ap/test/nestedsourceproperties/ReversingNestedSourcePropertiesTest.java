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


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.nestedsourceproperties._target.ChartEntry;
import org.mapstruct.ap.test.nestedsourceproperties.source.Artist;
import org.mapstruct.ap.test.nestedsourceproperties.source.Chart;
import org.mapstruct.ap.test.nestedsourceproperties.source.Label;
import org.mapstruct.ap.test.nestedsourceproperties.source.Song;
import org.mapstruct.ap.test.nestedsourceproperties.source.Studio;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Sjaak Derksen
 */
@WithClasses({ Song.class, Artist.class, Chart.class, Label.class, Studio.class, ChartEntry.class })
@IssueKey("65")
@RunWith(AnnotationProcessorTestRunner.class)
public class ReversingNestedSourcePropertiesTest {

    @Test
    @WithClasses({ ArtistToChartEntryReverse.class })
    public void shouldGenerateImplementationForPropertyNamesOnly() {

        Studio studio = new Studio();
        studio.setName( "Abbey Road" );
        studio.setCity( "London" );

        Label label = new Label();
        label.setStudio( studio );
        label.setName( "EMY" );

        Artist artist = new Artist();
        artist.setName( "The Beatles" );
        artist.setLabel( label );

        Song song = new Song();
        song.setArtist( artist );
        song.setTitle( "A Hard Day's Night" );

        ChartEntry chartEntry = ArtistToChartEntryReverse.MAPPER.mapForward( song );

        assertThat( chartEntry ).isNotNull();
        assertThat( chartEntry.getArtistName() ).isEqualTo( "The Beatles" );
        assertThat( chartEntry.getChartName() ).isNull();
        assertThat( chartEntry.getCity() ).isEqualTo( "London" );
        assertThat( chartEntry.getPosition() ).isEqualTo( 0 );
        assertThat( chartEntry.getRecordedAt() ).isEqualTo( "Abbey Road" );
        assertThat( chartEntry.getSongTitle() ).isEqualTo( "A Hard Day's Night" );

        // and now in reverse
        Song song2 = ArtistToChartEntryReverse.MAPPER.mapReverse( chartEntry );

        assertThat( song2 ).isNotNull();
        assertThat( song2.getArtist() ).isNotNull();
        assertThat( song2.getArtist().getName() ).isEqualTo( "The Beatles" );
        assertThat( song2.getArtist().getLabel() ).isNotNull();
        assertThat( song2.getArtist().getLabel().getName() ).isNull();
        assertThat( song2.getArtist().getLabel().getStudio() ).isNotNull();
        assertThat( song2.getArtist().getLabel().getStudio().getCity() ).isEqualTo( "London" );
        assertThat( song2.getArtist().getLabel().getStudio().getName() ).isEqualTo( "Abbey Road" );

    }

}
