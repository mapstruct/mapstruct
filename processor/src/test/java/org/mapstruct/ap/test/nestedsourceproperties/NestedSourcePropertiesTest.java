/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedsourceproperties;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.nestedsourceproperties._target.AdderUsageObserver;
import org.mapstruct.ap.test.nestedsourceproperties._target.ChartEntry;
import org.mapstruct.ap.test.nestedsourceproperties._target.ChartPositions;
import org.mapstruct.ap.test.nestedsourceproperties.source.Artist;
import org.mapstruct.ap.test.nestedsourceproperties.source.Chart;
import org.mapstruct.ap.test.nestedsourceproperties.source.Label;
import org.mapstruct.ap.test.nestedsourceproperties.source.Song;
import org.mapstruct.ap.test.nestedsourceproperties.source.Studio;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Sjaak Derksen
 */
@WithClasses({ Song.class, Artist.class, Chart.class, Label.class, Studio.class, ChartEntry.class })
@IssueKey("65")
public class NestedSourcePropertiesTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({ ArtistToChartEntry.class })
    public void shouldGenerateImplementationForPropertyNamesOnly() {
        generatedSource.addComparisonToFixtureFor( ArtistToChartEntry.class );

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

    @ProcessorTest
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

    @ProcessorTest
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

    @ProcessorTest
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

        assertThat( AdderUsageObserver.isUsed() ).isTrue();
    }

    @ProcessorTest
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

        assertThat( AdderUsageObserver.isUsed() ).isFalse();
    }

    @ProcessorTest
    @IssueKey("838")
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ArtistToChartEntryErroneous.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 34,
                message = "ArtistToChartEntryErroneous.ChartPosition does not have an accessible constructor.")
        }
    )
    @WithClasses({ ArtistToChartEntryErroneous.class })
    public void inverseShouldRaiseErrorForNotAccessibleConstructor() {
    }
}
