/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedsourceproperties;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.test.nestedsourceproperties._target.BaseChartEntry;
import org.mapstruct.ap.test.nestedsourceproperties._target.ChartEntry;
import org.mapstruct.ap.test.nestedsourceproperties._target.ChartEntryComposed;
import org.mapstruct.ap.test.nestedsourceproperties._target.ChartEntryLabel;
import org.mapstruct.ap.test.nestedsourceproperties._target.ChartEntryWithBase;
import org.mapstruct.ap.test.nestedsourceproperties._target.ChartEntryWithMapping;
import org.mapstruct.ap.test.nestedsourceproperties.source.Artist;
import org.mapstruct.ap.test.nestedsourceproperties.source.Chart;
import org.mapstruct.ap.test.nestedsourceproperties.source.Label;
import org.mapstruct.ap.test.nestedsourceproperties.source.Song;
import org.mapstruct.ap.test.nestedsourceproperties.source.SourceDtoFactory;
import org.mapstruct.ap.test.nestedsourceproperties.source.Studio;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Sjaak Derksen
 */
@IssueKey("389")
@WithClasses({ Song.class, Artist.class, Chart.class, Label.class, Studio.class, ChartEntry.class })
public class ReversingNestedSourcePropertiesTest {

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
    @WithClasses({ ArtistToChartEntryUpdateReverse.class })
    public void shouldGenerateNestedUpdateReverse() {

        Song song1 = prepareSong();

        ChartEntry chartEntry = ArtistToChartEntryUpdateReverse.MAPPER.mapForward( song1 );

        assertThat( chartEntry ).isNotNull();
        assertThat( chartEntry.getArtistName() ).isEqualTo( "The Beatles" );
        assertThat( chartEntry.getChartName() ).isNull();
        assertThat( chartEntry.getCity() ).isEqualTo( "London" );
        assertThat( chartEntry.getPosition() ).isEqualTo( 0 );
        assertThat( chartEntry.getRecordedAt() ).isEqualTo( "Abbey Road" );
        assertThat( chartEntry.getSongTitle() ).isEqualTo( "A Hard Day's Night" );

        // and now in reverse
        Song song2 = new Song();
        ArtistToChartEntryUpdateReverse.MAPPER.mapReverse( chartEntry, song2 );

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
    @WithClasses( { ArtistToChartEntryWithFactoryReverse.class, SourceDtoFactory.class } )
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

        assertThat( SourceDtoFactory.isCreateSongCalled() ).isTrue();
        assertThat( SourceDtoFactory.isCreateStudioCalled() ).isTrue();
        assertThat( SourceDtoFactory.isCreateLabelCalled() ).isTrue();
        assertThat( SourceDtoFactory.isCreateArtistCalled() ).isTrue();

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
        return song;
    }

}
