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
package org.mapstruct.ap.test.nestedtargetproperties;

import org.junit.Rule;
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
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Sjaak Derksen
 */
@WithClasses({
    Song.class,
    Artist.class,
    Chart.class,
    Label.class,
    Studio.class,
    ChartEntry.class,
    ChartEntryToArtist.class,
    ChartEntryToArtistUpdate.class
} )
@IssueKey("389")
@RunWith(AnnotationProcessorTestRunner.class)
public class NestedProductPropertiesTest {

    @Rule
    public GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor(
        ChartEntryToArtist.class,
        ChartEntryToArtistUpdate.class
    );

    @Test
    public void shouldMapNestedTarget() {

        ChartEntry chartEntry = new ChartEntry();
        chartEntry.setArtistName( "Prince" );
        chartEntry.setChartName( "US Billboard Hot Rock Songs" );
        chartEntry.setCity( "Minneapolis" );
        chartEntry.setPosition( 1 );
        chartEntry.setRecordedAt( "Live, First Avenue, Minneapolis" );
        chartEntry.setSongTitle( "Purple Rain" );

        Chart result = ChartEntryToArtist.MAPPER.map( chartEntry );

        assertThat( result.getName() ).isEqualTo( "US Billboard Hot Rock Songs" );
        assertThat( result.getSong() ).isNotNull();
        assertThat( result.getSong().getArtist() ).isNotNull();
        assertThat( result.getSong().getTitle() ).isEqualTo( "Purple Rain" );
        assertThat( result.getSong().getArtist().getName() ).isEqualTo( "Prince" );
        assertThat( result.getSong().getArtist().getLabel() ).isNotNull();
        assertThat( result.getSong().getArtist().getLabel().getStudio() ).isNotNull();
        assertThat( result.getSong().getArtist().getLabel().getStudio().getName() )
            .isEqualTo( "Live, First Avenue, Minneapolis" );
        assertThat( result.getSong().getArtist().getLabel().getStudio().getCity() )
            .isEqualTo( "Minneapolis" );
        assertThat( result.getSong().getPositions() ).hasSize( 1 );
        assertThat( result.getSong().getPositions().get( 0 ) ).isEqualTo( 1 );

    }

    @Test
    public void shouldMapNestedComposedTarget() {

        ChartEntry chartEntry1 = new ChartEntry();
        chartEntry1.setArtistName( "Prince" );
        chartEntry1.setCity( "Minneapolis" );
        chartEntry1.setRecordedAt( "Live, First Avenue, Minneapolis" );
        chartEntry1.setSongTitle( "Purple Rain" );

        ChartEntry chartEntry2 = new ChartEntry();
        chartEntry2.setChartName( "Italian Singles Chart" );
        chartEntry2.setPosition( 32 );

        Chart result = ChartEntryToArtist.MAPPER.map( chartEntry1, chartEntry2 );

        assertThat( result.getName() ).isEqualTo( "Italian Singles Chart" );
        assertThat( result.getSong() ).isNotNull();
        assertThat( result.getSong().getArtist() ).isNotNull();
        assertThat( result.getSong().getTitle() ).isEqualTo( "Purple Rain" );
        assertThat( result.getSong().getArtist().getName() ).isEqualTo( "Prince" );
        assertThat( result.getSong().getArtist().getLabel() ).isNotNull();
        assertThat( result.getSong().getArtist().getLabel().getStudio() ).isNotNull();
        assertThat( result.getSong().getArtist().getLabel().getStudio().getName() )
            .isEqualTo( "Live, First Avenue, Minneapolis" );
        assertThat( result.getSong().getArtist().getLabel().getStudio().getCity() )
            .isEqualTo( "Minneapolis" );
        assertThat( result.getSong().getPositions() ).hasSize( 1 );
        assertThat( result.getSong().getPositions().get( 0 ) ).isEqualTo( 32 );

    }

    @Test
    public void shouldReverseNestedTarget() {

        ChartEntry chartEntry = new ChartEntry();
        chartEntry.setArtistName( "Prince" );
        chartEntry.setChartName( "US Billboard Hot Rock Songs" );
        chartEntry.setCity( "Minneapolis" );
        chartEntry.setPosition( 1 );
        chartEntry.setRecordedAt( "Live, First Avenue, Minneapolis" );
        chartEntry.setSongTitle( "Purple Rain" );

        Chart chart = ChartEntryToArtist.MAPPER.map( chartEntry );
        ChartEntry result = ChartEntryToArtist.MAPPER.map( chart );

        assertThat( result ).isNotNull();
        assertThat( result.getArtistName() ).isEqualTo( "Prince" );
        assertThat( result.getChartName() ).isEqualTo( "US Billboard Hot Rock Songs" );
        assertThat( result.getCity() ).isEqualTo( "Minneapolis" );
        assertThat( result.getPosition() ).isEqualTo( 1 );
        assertThat( result.getRecordedAt() ).isEqualTo( "Live, First Avenue, Minneapolis" );
        assertThat( result.getSongTitle() ).isEqualTo( "Purple Rain" );
    }

    @Test
    public void shouldMapNestedTargetWitUpdate() {

        ChartEntry chartEntry = new ChartEntry();
        chartEntry.setArtistName( "Prince" );
        chartEntry.setChartName( "US Billboard Hot Rock Songs" );
        chartEntry.setCity( "Minneapolis" );
        chartEntry.setPosition( 1 );
        chartEntry.setRecordedAt( "Live, First Avenue, Minneapolis" );
        chartEntry.setSongTitle( null );

        Chart result = new Chart();
        result.setSong( new Song() );
        result.getSong().setTitle( "Raspberry Beret" );

        ChartEntryToArtistUpdate.MAPPER.map( chartEntry, result );

        assertThat( result.getName() ).isEqualTo( "US Billboard Hot Rock Songs" );
        assertThat( result.getSong() ).isNotNull();
        assertThat( result.getSong().getArtist() ).isNotNull();
        assertThat( result.getSong().getTitle() ).isEqualTo( "Raspberry Beret" );
        assertThat( result.getSong().getArtist().getName() ).isEqualTo( "Prince" );
        assertThat( result.getSong().getArtist().getLabel() ).isNotNull();
        assertThat( result.getSong().getArtist().getLabel().getStudio() ).isNotNull();
        assertThat( result.getSong().getArtist().getLabel().getStudio().getName() )
            .isEqualTo( "Live, First Avenue, Minneapolis" );
        assertThat( result.getSong().getArtist().getLabel().getStudio().getCity() )
            .isEqualTo( "Minneapolis" );
        assertThat( result.getSong().getPositions() ).hasSize( 1 );
        assertThat( result.getSong().getPositions().get( 0 ) ).isEqualTo( 1 );

    }
}
