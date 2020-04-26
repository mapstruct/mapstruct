/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.nestedsource._target;

/**
 * @author Filip Hrisafov
 */
public class ChartEntryWithBase extends BaseChartEntry {

    private final String recordedAt;
    private final String city;
    private final int position;

    public ChartEntryWithBase(String chartName, String songTitle, String artistName, String recordedAt,
        String city, int position) {
        super( chartName, songTitle, artistName );
        this.recordedAt = recordedAt;
        this.city = city;
        this.position = position;
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
