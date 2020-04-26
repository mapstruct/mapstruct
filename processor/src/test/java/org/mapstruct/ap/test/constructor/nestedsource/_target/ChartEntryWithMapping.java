/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.nestedsource._target;

/**
 * @author Filip Hrisafov
 */
public class ChartEntryWithMapping {

    private final String chartName;
    private final String songTitle;
    private final int artistId;
    private final String recordedAt;
    private final String city;
    private final int position;

    public ChartEntryWithMapping(String chartName, String songTitle, int artistId, String recordedAt,
        String city, int position) {
        this.chartName = chartName;
        this.songTitle = songTitle;
        this.artistId = artistId;
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

    public int getArtistId() {
        return artistId;
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
