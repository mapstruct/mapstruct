/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.nestedsource._target;

/**
 * @author Filip Hrisafov
 */
public class BaseChartEntry  {

    private final String chartName;
    private final String songTitle;
    private final String artistName;

    public BaseChartEntry(String chartName, String songTitle, String artistName) {
        this.chartName = chartName;
        this.songTitle = songTitle;
        this.artistName = artistName;
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
}
