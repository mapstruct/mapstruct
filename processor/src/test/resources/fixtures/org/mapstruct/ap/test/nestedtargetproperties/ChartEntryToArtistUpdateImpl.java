/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedtargetproperties;

import java.util.List;
import javax.annotation.processing.Generated;
import org.mapstruct.ap.test.nestedsourceproperties._target.ChartEntry;
import org.mapstruct.ap.test.nestedsourceproperties.source.Artist;
import org.mapstruct.ap.test.nestedsourceproperties.source.Chart;
import org.mapstruct.ap.test.nestedsourceproperties.source.Label;
import org.mapstruct.ap.test.nestedsourceproperties.source.Song;
import org.mapstruct.ap.test.nestedsourceproperties.source.Studio;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T14:56:17+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.11 (IBM Corporation)"
)
public class ChartEntryToArtistUpdateImpl extends ChartEntryToArtistUpdate {

    @Override
    public void map(ChartEntry chartEntry, Chart chart) {
        if ( chartEntry == null ) {
            return;
        }

        if ( chart.getSong() == null ) {
            chart.setSong( new Song() );
        }
        chartEntryToSong( chartEntry, chart.getSong() );
        String chartName = chartEntry.getChartName();
        if ( chartName != null ) {
            chart.setName( chartName );
        }
    }

    protected void chartEntryToStudio(ChartEntry chartEntry, Studio mappingTarget) {
        if ( chartEntry == null ) {
            return;
        }

        String recordedAt = chartEntry.getRecordedAt();
        if ( recordedAt != null ) {
            mappingTarget.setName( recordedAt );
        }
        String city = chartEntry.getCity();
        if ( city != null ) {
            mappingTarget.setCity( city );
        }
    }

    protected void chartEntryToLabel(ChartEntry chartEntry, Label mappingTarget) {
        if ( chartEntry == null ) {
            return;
        }

        if ( mappingTarget.getStudio() == null ) {
            mappingTarget.setStudio( new Studio() );
        }
        chartEntryToStudio( chartEntry, mappingTarget.getStudio() );
    }

    protected void chartEntryToArtist(ChartEntry chartEntry, Artist mappingTarget) {
        if ( chartEntry == null ) {
            return;
        }

        if ( mappingTarget.getLabel() == null ) {
            mappingTarget.setLabel( new Label() );
        }
        chartEntryToLabel( chartEntry, mappingTarget.getLabel() );
        String artistName = chartEntry.getArtistName();
        if ( artistName != null ) {
            mappingTarget.setName( artistName );
        }
    }

    protected void chartEntryToSong(ChartEntry chartEntry, Song mappingTarget) {
        if ( chartEntry == null ) {
            return;
        }

        if ( mappingTarget.getArtist() == null ) {
            mappingTarget.setArtist( new Artist() );
        }
        chartEntryToArtist( chartEntry, mappingTarget.getArtist() );
        String songTitle = chartEntry.getSongTitle();
        if ( songTitle != null ) {
            mappingTarget.setTitle( songTitle );
        }
        if ( mappingTarget.getPositions() != null ) {
            List<Integer> list = mapPosition( chartEntry.getPosition() );
            if ( list != null ) {
                mappingTarget.getPositions().clear();
                mappingTarget.getPositions().addAll( list );
            }
        }
        else {
            List<Integer> list = mapPosition( chartEntry.getPosition() );
            if ( list != null ) {
                mappingTarget.setPositions( list );
            }
        }
    }
}
