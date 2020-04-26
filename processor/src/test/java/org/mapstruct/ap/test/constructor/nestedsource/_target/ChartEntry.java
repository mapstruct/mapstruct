/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.nestedsource._target;

/**
 * @author Filip Hrisafov
 */
public class ChartEntry {

    private final String chartName;
    private final String songTitle;
    private final String artistName;
    private final String recordedAt;
    private final String city;
    private final int position;

    public ChartEntry(String chartName, String songTitle, String artistName, String recordedAt, String city,
        int position) {
        this.chartName = chartName;
        this.songTitle = songTitle;
        this.artistName = artistName;
        this.recordedAt = recordedAt;
        this.city = city;
        this.position = position;
    }

    public String getChartName() {
        return chartName;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getRecordedAt() {
        return recordedAt;
    }

    public String getCity() {
        return city;
    }

    public int getPosition() {
        return position;
    }

}
