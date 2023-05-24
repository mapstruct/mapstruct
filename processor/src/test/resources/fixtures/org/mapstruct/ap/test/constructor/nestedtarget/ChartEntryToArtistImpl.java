/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.nestedtarget;

import java.util.List;
import javax.annotation.processing.Generated;
import org.mapstruct.ap.test.constructor.nestedsource._target.ChartEntry;
import org.mapstruct.ap.test.constructor.nestedsource.source.Artist;
import org.mapstruct.ap.test.constructor.nestedsource.source.Chart;
import org.mapstruct.ap.test.constructor.nestedsource.source.Label;
import org.mapstruct.ap.test.constructor.nestedsource.source.Song;
import org.mapstruct.ap.test.constructor.nestedsource.source.Studio;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-04-19T14:54:28+0200",
    comments = "version: , compiler: javac, environment: Java 14.0.1 (Oracle Corporation)"
)
public class ChartEntryToArtistImpl extends ChartEntryToArtist {

    @Override
    public Chart map(ChartEntry chartEntry) {
        if ( chartEntry == null ) {
            return null;
        }

        Song song = null;
        String name = null;

        song = chartEntryToSong( chartEntry );
        name = chartEntry.getChartName();

        String type = null;

        Chart chart = new Chart( type, name, song );

        return chart;
    }

    @Override
    public ChartEntry map(Chart chart) {
        if ( chart == null ) {
            return null;
        }

        String chartName = null;
        String songTitle = null;
        String artistName = null;
        String recordedAt = null;
        String city = null;
        int position = 0;

        chartName = chart.getName();
        songTitle = chartSongTitle( chart );
        artistName = chartSongArtistName( chart );
        recordedAt = chartSongArtistLabelStudioName( chart );
        city = chartSongArtistLabelStudioCity( chart );
        position = mapPosition( chartSongPositions( chart ) );

        ChartEntry chartEntry = new ChartEntry( chartName, songTitle, artistName, recordedAt, city, position );

        return chartEntry;
    }

    protected Studio chartEntryToStudio(ChartEntry chartEntry) {
        if ( chartEntry == null ) {
            return null;
        }

        String name = null;
        String city = null;

        name = chartEntry.getRecordedAt();
        city = chartEntry.getCity();

        Studio studio = new Studio( name, city );

        return studio;
    }

    protected Label chartEntryToLabel(ChartEntry chartEntry) {
        if ( chartEntry == null ) {
            return null;
        }

        Studio studio = null;

        studio = chartEntryToStudio( chartEntry );

        String name = null;

        Label label = new Label( name, studio );

        return label;
    }

    protected Artist chartEntryToArtist(ChartEntry chartEntry) {
        if ( chartEntry == null ) {
            return null;
        }

        Label label = null;
        String name = null;

        label = chartEntryToLabel( chartEntry );
        name = chartEntry.getArtistName();

        Artist artist = new Artist( name, label );

        return artist;
    }

    protected Song chartEntryToSong(ChartEntry chartEntry) {
        if ( chartEntry == null ) {
            return null;
        }

        Artist artist = null;
        String title = null;
        List<Integer> positions = null;

        artist = chartEntryToArtist( chartEntry );
        title = chartEntry.getSongTitle();
        positions = mapPosition( chartEntry.getPosition() );

        Song song = new Song( artist, title, positions );

        return song;
    }

    private String chartSongTitle(Chart chart) {
        Song song = chart.getSong();
        if ( song == null ) {
            return null;
        }
        return song.getTitle();
    }

    private String chartSongArtistName(Chart chart) {
        Song song = chart.getSong();
        if ( song == null ) {
            return null;
        }
        Artist artist = song.getArtist();
        if ( artist == null ) {
            return null;
        }
        return artist.getName();
    }

    private String chartSongArtistLabelStudioName(Chart chart) {
        Song song = chart.getSong();
        if ( song == null ) {
            return null;
        }
        Artist artist = song.getArtist();
        if ( artist == null ) {
            return null;
        }
        Label label = artist.getLabel();
        if ( label == null ) {
            return null;
        }
        Studio studio = label.getStudio();
        if ( studio == null ) {
            return null;
        }
        return studio.getName();
    }

    private String chartSongArtistLabelStudioCity(Chart chart) {
        Song song = chart.getSong();
        if ( song == null ) {
            return null;
        }
        Artist artist = song.getArtist();
        if ( artist == null ) {
            return null;
        }
        Label label = artist.getLabel();
        if ( label == null ) {
            return null;
        }
        Studio studio = label.getStudio();
        if ( studio == null ) {
            return null;
        }
        return studio.getCity();
    }

    private List<Integer> chartSongPositions(Chart chart) {
        Song song = chart.getSong();
        if ( song == null ) {
            return null;
        }
        return song.getPositions();
    }
}
