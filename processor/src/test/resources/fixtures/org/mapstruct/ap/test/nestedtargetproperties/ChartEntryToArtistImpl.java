/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedtargetproperties;

import java.util.List;
import javax.annotation.Generated;
import org.mapstruct.ap.test.nestedsourceproperties._target.ChartEntry;
import org.mapstruct.ap.test.nestedsourceproperties.source.Artist;
import org.mapstruct.ap.test.nestedsourceproperties.source.Chart;
import org.mapstruct.ap.test.nestedsourceproperties.source.Label;
import org.mapstruct.ap.test.nestedsourceproperties.source.Song;
import org.mapstruct.ap.test.nestedsourceproperties.source.Studio;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-04-09T23:27:41+0200",
    comments = "version: , compiler: javac, environment: Java 1.8.0_121 (Oracle Corporation)"
)
public class ChartEntryToArtistImpl extends ChartEntryToArtist {

    @Override
    public Chart map(ChartEntry chartEntry) {
        if ( chartEntry == null ) {
            return null;
        }

        Chart chart = new Chart();

        chart.setSong( chartEntryToSong( chartEntry ) );
        chart.setName( chartEntry.getChartName() );

        return chart;
    }

    @Override
    public Chart map(ChartEntry chartEntry1, ChartEntry chartEntry2) {
        if ( chartEntry1 == null && chartEntry2 == null ) {
            return null;
        }

        Chart chart = new Chart();

        if ( chartEntry1 != null ) {
            if ( chart.getSong() == null ) {
                chart.setSong( new Song() );
            }
            chartEntryToSong1( chartEntry1, chart.getSong() );
        }
        if ( chartEntry2 != null ) {
            if ( chart.getSong() == null ) {
                chart.setSong( new Song() );
            }
            chartEntryToSong2( chartEntry2, chart.getSong() );
            chart.setName( chartEntry2.getChartName() );
        }

        return chart;
    }

    @Override
    public ChartEntry map(Chart chart) {
        if ( chart == null ) {
            return null;
        }

        ChartEntry chartEntry = new ChartEntry();

        chartEntry.setChartName( chart.getName() );
        chartEntry.setSongTitle( chartSongTitle( chart ) );
        chartEntry.setArtistName( chartSongArtistName( chart ) );
        chartEntry.setRecordedAt( chartSongArtistLabelStudioName( chart ) );
        chartEntry.setCity( chartSongArtistLabelStudioCity( chart ) );
        chartEntry.setPosition( mapPosition( chartSongPositions( chart ) ) );

        return chartEntry;
    }

    protected Studio chartEntryToStudio(ChartEntry chartEntry) {
        if ( chartEntry == null ) {
            return null;
        }

        Studio studio = new Studio();

        studio.setName( chartEntry.getRecordedAt() );
        studio.setCity( chartEntry.getCity() );

        return studio;
    }

    protected Label chartEntryToLabel(ChartEntry chartEntry) {
        if ( chartEntry == null ) {
            return null;
        }

        Label label = new Label();

        label.setStudio( chartEntryToStudio( chartEntry ) );

        return label;
    }

    protected Artist chartEntryToArtist(ChartEntry chartEntry) {
        if ( chartEntry == null ) {
            return null;
        }

        Artist artist = new Artist();

        artist.setLabel( chartEntryToLabel( chartEntry ) );
        artist.setName( chartEntry.getArtistName() );

        return artist;
    }

    protected Song chartEntryToSong(ChartEntry chartEntry) {
        if ( chartEntry == null ) {
            return null;
        }

        Song song = new Song();

        song.setArtist( chartEntryToArtist( chartEntry ) );
        song.setTitle( chartEntry.getSongTitle() );
        song.setPositions( mapPosition( chartEntry.getPosition() ) );

        return song;
    }

    protected void chartEntryToStudio1(ChartEntry chartEntry, Studio mappingTarget) {
        if ( chartEntry == null ) {
            return;
        }

        mappingTarget.setName( chartEntry.getRecordedAt() );
        mappingTarget.setCity( chartEntry.getCity() );
    }

    protected void chartEntryToLabel1(ChartEntry chartEntry, Label mappingTarget) {
        if ( chartEntry == null ) {
            return;
        }

        if ( mappingTarget.getStudio() == null ) {
            mappingTarget.setStudio( new Studio() );
        }
        chartEntryToStudio1( chartEntry, mappingTarget.getStudio() );
    }

    protected void chartEntryToArtist1(ChartEntry chartEntry, Artist mappingTarget) {
        if ( chartEntry == null ) {
            return;
        }

        if ( mappingTarget.getLabel() == null ) {
            mappingTarget.setLabel( new Label() );
        }
        chartEntryToLabel1( chartEntry, mappingTarget.getLabel() );
        mappingTarget.setName( chartEntry.getArtistName() );
    }

    protected void chartEntryToSong1(ChartEntry chartEntry, Song mappingTarget) {
        if ( chartEntry == null ) {
            return;
        }

        if ( mappingTarget.getArtist() == null ) {
            mappingTarget.setArtist( new Artist() );
        }
        chartEntryToArtist1( chartEntry, mappingTarget.getArtist() );
        mappingTarget.setTitle( chartEntry.getSongTitle() );
    }

    protected void chartEntryToSong2(ChartEntry chartEntry, Song mappingTarget) {
        if ( chartEntry == null ) {
            return;
        }

        if ( mappingTarget.getPositions() != null ) {
            List<Integer> list = mapPosition( chartEntry.getPosition() );
            if ( list != null ) {
                mappingTarget.getPositions().clear();
                mappingTarget.getPositions().addAll( list );
            }
            else {
                mappingTarget.setPositions( null );
            }
        }
        else {
            List<Integer> list = mapPosition( chartEntry.getPosition() );
            if ( list != null ) {
                mappingTarget.setPositions( list );
            }
        }
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
