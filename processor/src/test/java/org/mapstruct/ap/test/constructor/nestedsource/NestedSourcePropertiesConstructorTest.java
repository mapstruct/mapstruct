/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.nestedsource;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

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
@WithClasses({ Song.class, Artist.class, Chart.class, Label.class, Studio.class, ChartEntry.class })
@IssueKey("73")
public class NestedSourcePropertiesConstructorTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({ ArtistToChartEntry.class })
    public void shouldGenerateImplementationForPropertyNamesOnly() {
        generatedSource.addComparisonToFixtureFor( ArtistToChartEntry.class );

        Studio studio = new Studio( "Abbey Road", "London" );

        Label label = new Label( "EMY", studio );

        Artist artist = new Artist( "The Beatles", label );

        Song song = new Song( artist, "A Hard Day's Night", Collections.emptyList() );

        ChartEntry chartEntry = ArtistToChartEntry.MAPPER.map( song );

        assertThat( chartEntry ).isNotNull();
        assertThat( chartEntry.getArtistName() ).isEqualTo( "The Beatles" );
        assertThat( chartEntry.getChartName() ).isNull();
        assertThat( chartEntry.getCity() ).isEqualTo( "London" );
        assertThat( chartEntry.getPosition() ).isEqualTo( 0 );
        assertThat( chartEntry.getRecordedAt() ).isEqualTo( "Abbey Road" );
        assertThat( chartEntry.getSongTitle() ).isEqualTo( "A Hard Day's Night" );
    }

    @ProcessorTest
    @WithClasses({ ArtistToChartEntry.class })
    public void shouldGenerateImplementationForMultipleParam() {

        Studio studio = new Studio( "Abbey Road", "London" );

        Label label = new Label( "EMY", studio );

        Artist artist = new Artist( "The Beatles", label );

        Song song = new Song( artist, "A Hard Day's Night", Collections.emptyList() );

        Chart chart = new Chart( "record-sales", "Billboard", null );

        ChartEntry chartEntry = ArtistToChartEntry.MAPPER.map( chart, song, 1 );

        assertThat( chartEntry ).isNotNull();
        assertThat( chartEntry.getArtistName() ).isEqualTo( "The Beatles" );
        assertThat( chartEntry.getChartName() ).isEqualTo( "Billboard" );
        assertThat( chartEntry.getCity() ).isEqualTo( "London" );
        assertThat( chartEntry.getPosition() ).isEqualTo( 1 );
        assertThat( chartEntry.getRecordedAt() ).isEqualTo( "Abbey Road" );
        assertThat( chartEntry.getSongTitle() ).isEqualTo( "A Hard Day's Night" );

        chartEntry = ArtistToChartEntry.MAPPER.map( null, song, 10 );

        assertThat( chartEntry ).isNotNull();
        assertThat( chartEntry.getArtistName() ).isEqualTo( "The Beatles" );
        assertThat( chartEntry.getChartName() ).isNull();
        assertThat( chartEntry.getCity() ).isEqualTo( "London" );
        assertThat( chartEntry.getPosition() ).isEqualTo( 10 );
        assertThat( chartEntry.getRecordedAt() ).isEqualTo( "Abbey Road" );
        assertThat( chartEntry.getSongTitle() ).isEqualTo( "A Hard Day's Night" );

        chartEntry = ArtistToChartEntry.MAPPER.map( chart, null, 5 );

        assertThat( chartEntry ).isNotNull();
        assertThat( chartEntry.getArtistName() ).isNull();
        assertThat( chartEntry.getChartName() ).isEqualTo( "Billboard" );
        assertThat( chartEntry.getCity() ).isNull();
        assertThat( chartEntry.getPosition() ).isEqualTo( 5 );
        assertThat( chartEntry.getRecordedAt() ).isNull();
        assertThat( chartEntry.getSongTitle() ).isNull();

        chartEntry = ArtistToChartEntry.MAPPER.map( chart, song, null );

        assertThat( chartEntry ).isNotNull();
        assertThat( chartEntry.getArtistName() ).isEqualTo( "The Beatles" );
        assertThat( chartEntry.getChartName() ).isEqualTo( "Billboard" );
        assertThat( chartEntry.getCity() ).isEqualTo( "London" );
        assertThat( chartEntry.getPosition() ).isEqualTo( 0 );
        assertThat( chartEntry.getRecordedAt() ).isEqualTo( "Abbey Road" );
        assertThat( chartEntry.getSongTitle() ).isEqualTo( "A Hard Day's Night" );
    }

    @ProcessorTest
    @WithClasses({ ArtistToChartEntry.class })
    public void shouldPickPropertyNameOverParameterName() {

        Chart chart = new Chart( "record-sales", "Billboard", null );

        ChartEntry chartEntry = ArtistToChartEntry.MAPPER.map( chart );

        assertThat( chartEntry ).isNotNull();
        assertThat( chartEntry.getArtistName() ).isNull();
        assertThat( chartEntry.getChartName() ).isEqualTo( "Billboard" );
        assertThat( chartEntry.getCity() ).isNull();
        assertThat( chartEntry.getPosition() ).isEqualTo( 0 );
        assertThat( chartEntry.getRecordedAt() ).isNull();
        assertThat( chartEntry.getSongTitle() ).isNull();
    }
}
