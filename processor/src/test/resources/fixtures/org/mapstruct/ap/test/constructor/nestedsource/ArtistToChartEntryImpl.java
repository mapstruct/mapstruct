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

        String chartName;
        if ( chart != null ) {
            chartName = chart.getName();
        }
        else {
            chartName = null;
        }
        String songTitle;
        String artistName;
        String recordedAt;
        String city;
        if ( song != null ) {
            songTitle = song.getTitle();
            artistName = songArtistName( song );
            recordedAt = songArtistLabelStudioName( song );
            city = songArtistLabelStudioCity( song );
        }
        else {
            songTitle = null;
            artistName = null;
            recordedAt = null;
            city = null;
        }
        int position1;
        if ( position != null ) {
            position1 = position;
        }
        else {
            position1 = 0;
        }

        ChartEntry chartEntry = new ChartEntry( chartName, songTitle, artistName, recordedAt, city, position1 );

        return chartEntry;
    }

    @Override
    public ChartEntry map(Song song) {
        if ( song == null ) {
            return null;
        }

        String songTitle;
        String artistName;
        String recordedAt;
        String city;

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

        String chartName;

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
        if ( song == null ) {
            return null;
        }
        Artist artist = song.getArtist();
        if ( artist == null ) {
            return null;
        }
        String name = artist.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String songArtistLabelStudioName(Song song) {
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
        String name = studio.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String songArtistLabelStudioCity(Song song) {
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
        String city = studio.getCity();
        if ( city == null ) {
            return null;
        }
        return city;
    }
}
