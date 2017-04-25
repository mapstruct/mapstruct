/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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

        String title = chartSongTitle( chart );
        if ( title != null ) {
            chartEntry.setSongTitle( title );
        }
        chartEntry.setChartName( chart.getName() );
        String city = chartSongArtistLabelStudioCity( chart );
        if ( city != null ) {
            chartEntry.setCity( city );
        }
        String name = chartSongArtistLabelStudioName( chart );
        if ( name != null ) {
            chartEntry.setRecordedAt( name );
        }
        String name1 = chartSongArtistName( chart );
        if ( name1 != null ) {
            chartEntry.setArtistName( name1 );
        }
        List<Integer> positions = chartSongPositions( chart );
        if ( positions != null ) {
            chartEntry.setPosition( mapPosition( positions ) );
        }

        return chartEntry;
    }

    protected Studio chartEntryToStudio(ChartEntry chartEntry) {
        if ( chartEntry == null ) {
            return null;
        }

        Studio studio = new Studio();

        studio.setCity( chartEntry.getCity() );
        studio.setName( chartEntry.getRecordedAt() );

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
        song.setPositions( mapPosition( chartEntry.getPosition() ) );
        song.setTitle( chartEntry.getSongTitle() );

        return song;
    }

    protected void chartEntryToStudio1(ChartEntry chartEntry, Studio mappingTarget) {
        if ( chartEntry == null ) {
            return;
        }

        mappingTarget.setCity( chartEntry.getCity() );
        mappingTarget.setName( chartEntry.getRecordedAt() );
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
        if ( chart == null ) {
            return null;
        }
        Song song = chart.getSong();
        if ( song == null ) {
            return null;
        }
        String title = song.getTitle();
        if ( title == null ) {
            return null;
        }
        return title;
    }

    private String chartSongArtistLabelStudioCity(Chart chart) {
        if ( chart == null ) {
            return null;
        }
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
        String city = studio.getCity();
        if ( city == null ) {
            return null;
        }
        return city;
    }

    private String chartSongArtistLabelStudioName(Chart chart) {
        if ( chart == null ) {
            return null;
        }
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
        String name = studio.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String chartSongArtistName(Chart chart) {
        if ( chart == null ) {
            return null;
        }
        Song song = chart.getSong();
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

    private List<Integer> chartSongPositions(Chart chart) {
        if ( chart == null ) {
            return null;
        }
        Song song = chart.getSong();
        if ( song == null ) {
            return null;
        }
        List<Integer> positions = song.getPositions();
        if ( positions == null ) {
            return null;
        }
        return positions;
    }
}
