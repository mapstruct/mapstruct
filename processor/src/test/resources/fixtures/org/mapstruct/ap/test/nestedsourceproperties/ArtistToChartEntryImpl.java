/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedsourceproperties;

import javax.annotation.Generated;
import org.mapstruct.ap.test.nestedsourceproperties._target.ChartEntry;
import org.mapstruct.ap.test.nestedsourceproperties.source.Artist;
import org.mapstruct.ap.test.nestedsourceproperties.source.Chart;
import org.mapstruct.ap.test.nestedsourceproperties.source.Label;
import org.mapstruct.ap.test.nestedsourceproperties.source.Song;
import org.mapstruct.ap.test.nestedsourceproperties.source.Studio;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-02-17T20:44:15+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_112 (Oracle Corporation)"
)
public class ArtistToChartEntryImpl implements ArtistToChartEntry {

    @Override
    public ChartEntry map(Chart chart, Song song, Integer position) {
        if ( chart == null && song == null && position == null ) {
            return null;
        }

        ChartEntry chartEntry = new ChartEntry();

        if ( chart != null ) {
            chartEntry.setChartName( chart.getName() );
        }
        if ( song != null ) {
            chartEntry.setSongTitle( song.getTitle() );
            chartEntry.setArtistName( songArtistName( song ) );
            chartEntry.setRecordedAt( songArtistLabelStudioName( song ) );
            chartEntry.setCity( songArtistLabelStudioCity( song ) );
        }
        if ( position != null ) {
            chartEntry.setPosition( position );
        }

        return chartEntry;
    }

    @Override
    public ChartEntry map(Song song) {
        if ( song == null ) {
            return null;
        }

        ChartEntry chartEntry = new ChartEntry();

        chartEntry.setSongTitle( song.getTitle() );
        chartEntry.setArtistName( songArtistName( song ) );
        chartEntry.setRecordedAt( songArtistLabelStudioName( song ) );
        chartEntry.setCity( songArtistLabelStudioCity( song ) );

        return chartEntry;
    }

    @Override
    public ChartEntry map(Chart name) {
        if ( name == null ) {
            return null;
        }

        ChartEntry chartEntry = new ChartEntry();

        chartEntry.setChartName( name.getName() );

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
