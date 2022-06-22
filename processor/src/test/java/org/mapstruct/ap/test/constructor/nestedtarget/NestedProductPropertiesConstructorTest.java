/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.nestedtarget;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.constructor.nestedsource._target.ChartEntry;
import org.mapstruct.ap.test.constructor.nestedsource.source.Artist;
import org.mapstruct.ap.test.constructor.nestedsource.source.Chart;
import org.mapstruct.ap.test.constructor.nestedsource.source.Label;
import org.mapstruct.ap.test.constructor.nestedsource.source.Song;
import org.mapstruct.ap.test.constructor.nestedsource.source.Studio;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
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
})
@IssueKey("73")
public class NestedProductPropertiesConstructorTest {

    @RegisterExtension
    GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor(
        ChartEntryToArtist.class
    );

    @ProcessorTest
    public void shouldMapNestedTarget() {

        ChartEntry chartEntry = new ChartEntry(
            "US Billboard Hot Rock Songs",
            "Purple Rain",
            "Prince",
            "Live, First Avenue, Minneapolis",
            "Minneapolis",
            1
        );

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

    @ProcessorTest
    public void shouldReverseNestedTarget() {

        ChartEntry chartEntry = new ChartEntry(
            "US Billboard Hot Rock Songs",
            "Purple Rain",
            "Prince",
            "Live, First Avenue, Minneapolis",
            "Minneapolis",
            1
        );

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
}
