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

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.nestedsourceproperties._target.AdderUsageObserver;
import org.mapstruct.ap.test.nestedsourceproperties._target.ChartEntry;
import org.mapstruct.ap.test.nestedsourceproperties._target.ChartPositions;
import org.mapstruct.ap.test.nestedsourceproperties.source.Artist;
import org.mapstruct.ap.test.nestedsourceproperties.source.Chart;
import org.mapstruct.ap.test.nestedsourceproperties.source.Label;
import org.mapstruct.ap.test.nestedsourceproperties.source.Song;
import org.mapstruct.ap.test.nestedsourceproperties.source.Studio;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Sjaak Derksen
 */
@WithClasses({ Song.class, Artist.class, Chart.class, Label.class, Studio.class, ChartEntry.class })
@IssueKey("65")
@RunWith(AnnotationProcessorTestRunner.class)
public class NestedSourcePropertiesTest {

    @Test
    @WithClasses({ ArtistToChartEntry.class })
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

        ChartEntry chartEntry = ArtistToChartEntry.MAPPER.map( song );

        assertThat( chartEntry ).isNotNull();
        assertThat( chartEntry.getArtistName() ).isEqualTo( "The Beatles" );
        assertThat( chartEntry.getChartName() ).isNull();
        assertThat( chartEntry.getCity() ).isEqualTo( "London" );
        assertThat( chartEntry.getPosition() ).isEqualTo( 0 );
        assertThat( chartEntry.getRecordedAt() ).isEqualTo( "Abbey Road" );
        assertThat( chartEntry.getSongTitle() ).isEqualTo( "A Hard Day's Night" );
    }

    @Test
    @WithClasses({ ArtistToChartEntry.class })
    public void shouldGenerateImplementationForMultipleParam() {

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

        Chart chart = new Chart();
        chart.setName( "Billboard" );
        chart.setType( "record-sales" );

        ChartEntry chartEntry = ArtistToChartEntry.MAPPER.map( chart, song, 1 );

        assertThat( chartEntry ).isNotNull();
        assertThat( chartEntry.getArtistName() ).isEqualTo( "The Beatles" );
        assertThat( chartEntry.getChartName() ).isEqualTo( "Billboard" );
        assertThat( chartEntry.getCity() ).isEqualTo( "London" );
        assertThat( chartEntry.getPosition() ).isEqualTo( 1 );
        assertThat( chartEntry.getRecordedAt() ).isEqualTo( "Abbey Road" );
        assertThat( chartEntry.getSongTitle() ).isEqualTo( "A Hard Day's Night" );
    }

    @Test
    @WithClasses({ ArtistToChartEntry.class })
    public void shouldPickPropertyNameOverParameterName() {

        Chart chart = new Chart();
        chart.setName( "Billboard" );
        chart.setType( "record-sales" );

        ChartEntry chartEntry = ArtistToChartEntry.MAPPER.map( chart );

        assertThat( chartEntry ).isNotNull();
        assertThat( chartEntry.getArtistName() ).isNull();
        assertThat( chartEntry.getChartName() ).isEqualTo( "Billboard" );
        assertThat( chartEntry.getCity() ).isNull();
        assertThat( chartEntry.getPosition() ).isEqualTo( 0 );
        assertThat( chartEntry.getRecordedAt() ).isNull();
        assertThat( chartEntry.getSongTitle() ).isNull();
    }

    @Test
    @WithClasses({ ArtistToChartEntryAdder.class, ChartPositions.class, AdderUsageObserver.class })
    public void shouldUseAddAsTargetAccessor() {

        AdderUsageObserver.setUsed( false );
        Song song = new Song();
        song.setPositions( Arrays.asList( 3, 5 ) );

        Chart chart = new Chart();
        chart.setSong( song );

        ChartPositions positions = ArtistToChartEntryAdder.MAPPER.map( chart );
        assertThat( positions ).isNotNull();
        assertThat( positions.getPositions() ).containsExactly( 3L, 5L );

        assertTrue( AdderUsageObserver.isUsed() );
    }

    @Test
    @WithClasses({ ArtistToChartEntryGetter.class, ChartPositions.class, AdderUsageObserver.class })
    public void shouldUseGetAsTargetAccessor() {

        AdderUsageObserver.setUsed( false );
        Song song = new Song();
        song.setPositions( Arrays.asList( 3, 5 ) );

        Chart chart = new Chart();
        chart.setSong( song );

        ChartPositions positions = ArtistToChartEntryGetter.MAPPER.map( chart );
        assertThat( positions ).isNotNull();
        assertThat( positions.getPositions() ).containsExactly( 3L, 5L );

        assertFalse( AdderUsageObserver.isUsed() );
    }


    @Test
    @IssueKey("337")
    @WithClasses({ ArtistToChartEntry.class })
    public void reverseShouldIgnoreNestedProperties() {

        ChartEntry entry = new ChartEntry();
        entry.setSongTitle( "Another brick in the wall" );

        Song song = ArtistToChartEntry.MAPPER.map( entry );
        assertThat( song ).isNotNull();
        assertThat( song.getTitle() ).isEqualTo( "Another brick in the wall" );
    }

    @Test
    @IssueKey( "337" )
    @ExpectedCompilationOutcome(
             value = CompilationResult.FAILED,
            diagnostics = {
                @Diagnostic( type = ArtistToChartEntryErroneous.class,
                        kind = javax.tools.Diagnostic.Kind.ERROR,
                        line = 47,
                        messageRegExp = "Parameter java.lang.Integer position cannot be reversed" )
            }
    )
    @WithClasses({ ArtistToChartEntryErroneous.class })
    public void reverseShouldIgnoreParameter() {
    }
}
