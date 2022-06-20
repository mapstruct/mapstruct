/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.nestedsource;

import java.util.Collections;

import org.mapstruct.ap.test.constructor.nestedsource._target.BaseChartEntry;
import org.mapstruct.ap.test.constructor.nestedsource._target.ChartEntry;
import org.mapstruct.ap.test.constructor.nestedsource._target.ChartEntryComposed;
import org.mapstruct.ap.test.constructor.nestedsource._target.ChartEntryLabel;
import org.mapstruct.ap.test.constructor.nestedsource._target.ChartEntryWithBase;
import org.mapstruct.ap.test.constructor.nestedsource._target.ChartEntryWithMapping;
import org.mapstruct.ap.test.constructor.nestedsource.source.Artist;
import org.mapstruct.ap.test.constructor.nestedsource.source.Chart;
import org.mapstruct.ap.test.constructor.nestedsource.source.Label;
import org.mapstruct.ap.test.constructor.nestedsource.source.Song;
import org.mapstruct.ap.test.constructor.nestedsource.source.Studio;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("73")
@WithClasses({ Song.class, Artist.class, Chart.class, Label.class, Studio.class, ChartEntry.class })
public class ReversingNestedSourcePropertiesConstructorTest {

    @ProcessorTest
    @WithClasses({ ArtistToChartEntryReverse.class })
    public void shouldGenerateNestedReverse() {

        Song song1 = prepareSong();

        ChartEntry chartEntry = ArtistToChartEntryReverse.MAPPER.mapForward( song1 );

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

    @ProcessorTest
    @WithClasses({ ArtistToChartEntryWithIgnoresReverse.class })
    public void shouldIgnoreEverytingBelowArtist() {

        Song song1 = prepareSong();

        ChartEntry chartEntry = ArtistToChartEntryWithIgnoresReverse.MAPPER.mapForward( song1 );

        assertThat( chartEntry ).isNotNull();
        assertThat( chartEntry.getArtistName() ).isEqualTo( "The Beatles" );
        assertThat( chartEntry.getChartName() ).isNull();
        assertThat( chartEntry.getCity() ).isEqualTo( "London" );
        assertThat( chartEntry.getPosition() ).isEqualTo( 0 );
        assertThat( chartEntry.getRecordedAt() ).isEqualTo( "Abbey Road" );
        assertThat( chartEntry.getSongTitle() ).isEqualTo( "A Hard Day's Night" );

        // and now in reverse
        Song song2 = ArtistToChartEntryWithIgnoresReverse.MAPPER.mapReverse( chartEntry );

        assertThat( song2 ).isNotNull();
        assertThat( song2.getArtist() ).isNull();
    }

    @ProcessorTest
    @WithClasses({ ArtistToChartEntryWithFactoryReverse.class })
    public void shouldGenerateNestedReverseWithFactory() {

        Song song1 = prepareSong();

        ChartEntry chartEntry = ArtistToChartEntryWithFactoryReverse.MAPPER.mapForward( song1 );

        assertThat( chartEntry ).isNotNull();
        assertThat( chartEntry.getArtistName() ).isEqualTo( "The Beatles" );
        assertThat( chartEntry.getChartName() ).isNull();
        assertThat( chartEntry.getCity() ).isEqualTo( "London" );
        assertThat( chartEntry.getPosition() ).isEqualTo( 0 );
        assertThat( chartEntry.getRecordedAt() ).isEqualTo( "Abbey Road" );
        assertThat( chartEntry.getSongTitle() ).isEqualTo( "A Hard Day's Night" );

        // and now in reverse
        Song song2 = ArtistToChartEntryWithFactoryReverse.MAPPER.mapReverse( chartEntry );

        assertThat( song2 ).isNotNull();
        assertThat( song2.getArtist() ).isNotNull();
        assertThat( song2.getArtist().getName() ).isEqualTo( "The Beatles" );
        assertThat( song2.getArtist().getLabel() ).isNotNull();
        assertThat( song2.getArtist().getLabel().getName() ).isNull();
        assertThat( song2.getArtist().getLabel().getStudio() ).isNotNull();
        assertThat( song2.getArtist().getLabel().getStudio().getCity() ).isEqualTo( "London" );
        assertThat( song2.getArtist().getLabel().getStudio().getName() ).isEqualTo( "Abbey Road" );

    }

    @ProcessorTest
    @WithClasses({ ArtistToChartEntryComposedReverse.class, ChartEntryComposed.class, ChartEntryLabel.class })
    public void shouldGenerateNestedComposedReverse() {

        Song song1 = prepareSong();

        ChartEntryComposed chartEntry = ArtistToChartEntryComposedReverse.MAPPER.mapForward( song1 );

        assertThat( chartEntry ).isNotNull();
        assertThat( chartEntry.getArtistName() ).isEqualTo( "The Beatles" );
        assertThat( chartEntry.getChartName() ).isNull();
        assertThat( chartEntry.getLabel().getName() ).isEqualTo( "EMY" );
        assertThat( chartEntry.getLabel().getCity() ).isEqualTo( "London" );
        assertThat( chartEntry.getLabel().getRecordedAt() ).isEqualTo( "Abbey Road" );
        assertThat( chartEntry.getPosition() ).isEqualTo( 0 );
        assertThat( chartEntry.getSongTitle() ).isEqualTo( "A Hard Day's Night" );

        // and now in reverse
        Song song2 = ArtistToChartEntryComposedReverse.MAPPER.mapReverse( chartEntry );

        assertThat( song2 ).isNotNull();
        assertThat( song2.getArtist() ).isNotNull();
        assertThat( song2.getArtist().getName() ).isEqualTo( "The Beatles" );
        assertThat( song2.getArtist().getLabel() ).isNotNull();
        assertThat( song2.getArtist().getLabel().getName() ).isEqualTo( "EMY" );
        assertThat( song2.getArtist().getLabel().getStudio() ).isNotNull();
        assertThat( song2.getArtist().getLabel().getStudio().getCity() ).isEqualTo( "London" );
        assertThat( song2.getArtist().getLabel().getStudio().getName() ).isEqualTo( "Abbey Road" );
    }

    @ProcessorTest
    @WithClasses({ ArtistToChartEntryWithMappingReverse.class, ChartEntryWithMapping.class })
    public void shouldGenerateNestedWithMappingReverse() {

        Song song1 = prepareSong();

        ChartEntryWithMapping chartEntry = ArtistToChartEntryWithMappingReverse.MAPPER.mapForward( song1 );

        assertThat( chartEntry ).isNotNull();
        assertThat( chartEntry.getArtistId() ).isEqualTo( 1 );
        assertThat( chartEntry.getChartName() ).isNull();
        assertThat( chartEntry.getCity() ).isEqualTo( "London" );
        assertThat( chartEntry.getPosition() ).isEqualTo( 0 );
        assertThat( chartEntry.getRecordedAt() ).isEqualTo( "Abbey Road" );
        assertThat( chartEntry.getSongTitle() ).isEqualTo( "A Hard Day's Night" );

        // and now in reverse
        Song song2 = ArtistToChartEntryWithMappingReverse.MAPPER.mapReverse( chartEntry );

        assertThat( song2 ).isNotNull();
        assertThat( song2.getArtist() ).isNotNull();
        assertThat( song2.getArtist().getName() ).isEqualTo( "The Beatles" );
        assertThat( song2.getArtist().getLabel() ).isNotNull();
        assertThat( song2.getArtist().getLabel().getName() ).isNull();
        assertThat( song2.getArtist().getLabel().getStudio() ).isNotNull();
        assertThat( song2.getArtist().getLabel().getStudio().getCity() ).isEqualTo( "London" );
        assertThat( song2.getArtist().getLabel().getStudio().getName() ).isEqualTo( "Abbey Road" );
    }

    @ProcessorTest
    @WithClasses({
        ArtistToChartEntryWithConfigReverse.class,
        ArtistToChartEntryConfig.class,
        BaseChartEntry.class,
        ChartEntryWithBase.class
    })
    public void shouldGenerateNestedWithConfigReverse() {

        Song song1 = prepareSong();

        ChartEntryWithBase chartEntry = ArtistToChartEntryWithConfigReverse.MAPPER.mapForward( song1 );

        assertThat( chartEntry ).isNotNull();
        assertThat( chartEntry.getArtistName() ).isEqualTo( "The Beatles" );
        assertThat( chartEntry.getChartName() ).isNull();
        assertThat( chartEntry.getCity() ).isEqualTo( "London" );
        assertThat( chartEntry.getPosition() ).isEqualTo( 0 );
        assertThat( chartEntry.getRecordedAt() ).isEqualTo( "Abbey Road" );
        assertThat( chartEntry.getSongTitle() ).isEqualTo( "A Hard Day's Night" );

        // and now in reverse
        Song song2 = ArtistToChartEntryWithConfigReverse.MAPPER.mapReverse( chartEntry );

        assertThat( song2 ).isNotNull();
        assertThat( song2.getArtist() ).isNotNull();
        assertThat( song2.getArtist().getName() ).isEqualTo( "The Beatles" );
        assertThat( song2.getArtist().getLabel() ).isNotNull();
        assertThat( song2.getArtist().getLabel().getName() ).isNull();
        assertThat( song2.getArtist().getLabel().getStudio() ).isNotNull();
        assertThat( song2.getArtist().getLabel().getStudio().getCity() ).isEqualTo( "London" );
        assertThat( song2.getArtist().getLabel().getStudio().getName() ).isEqualTo( "Abbey Road" );
    }

    private Song prepareSong() {
        Studio studio = new Studio( "Abbey Road", "London" );

        Label label = new Label( "EMY", studio );

        Artist artist = new Artist( "The Beatles", label );

        return new Song( artist, "A Hard Day's Night", Collections.emptyList() );
    }

}
