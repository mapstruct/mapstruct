/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.nestedsource;

import javax.annotation.processing.Generated;
import org.mapstruct.ap.test.constructor.nestedsource._target.ChartEntry;
import org.mapstruct.ap.test.constructor.nestedsource.source.Artist;
import org.mapstruct.ap.test.constructor.nestedsource.source.Chart;
import org.mapstruct.ap.test.constructor.nestedsource.source.Label;
import org.mapstruct.ap.test.constructor.nestedsource.source.Song;
import org.mapstruct.ap.test.constructor.nestedsource.source.Studio;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-04-19T11:28:54+0200",
    comments = "version: , compiler: javac, environment: Java 14.0.1 (Oracle Corporation)"
)
public class ArtistToChartEntryImpl implements ArtistToChartEntry {

    @Override
    public ChartEntry map(Chart chart, Song song, Integer position) {
        if ( chart == null && song == null && position == null ) {
            return null;
        }

        String chartName = null;
        if ( chart != null ) {
            chartName = chart.getName();
        }
        String songTitle = null;
        String artistName = null;
        String recordedAt = null;
        String city = null;
        if ( song != null ) {
            songTitle = song.getTitle();
            artistName = songArtistName( song );
            recordedAt = songArtistLabelStudioName( song );
            city = songArtistLabelStudioCity( song );
        }
        int position1 = 0;
        if ( position != null ) {
            position1 = position;
        }

        ChartEntry chartEntry = new ChartEntry( chartName, songTitle, artistName, recordedAt, city, position1 );

        return chartEntry;
    }

    @Override
    public ChartEntry map(Song song) {
        if ( song == null ) {
            return null;
        }

        String songTitle = null;
        String artistName = null;
        String recordedAt = null;
        String city = null;

        songTitle = song.getTitle();
        artistName = songArtistName( song );
        recordedAt = songArtistLabelStudioName( song );
        city = songArtistLabelStudioCity( song );

        String chartName = null;
        int position = 0;

        ChartEntry chartEntry = new ChartEntry( chartName, songTitle, artistName, recordedAt, city, position );

        return chartEntry;
    }

    @Override
    public ChartEntry map(Chart name) {
        if ( name == null ) {
            return null;
        }

        String chartName = null;

        chartName = name.getName();

        String songTitle = null;
        String artistName = null;
        String recordedAt = null;
        String city = null;
        int position = 0;

        ChartEntry chartEntry = new ChartEntry( chartName, songTitle, artistName, recordedAt, city, position );

        return chartEntry;
    }

    private String songArtistName(Song song) {
        Artist artist = song.getArtist();
        if ( artist == null ) {
            return null;
        }
        return artist.getName();
    }

    private String songArtistLabelStudioName(Song song) {
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

    private String songArtistLabelStudioCity(Song song) {
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
}
