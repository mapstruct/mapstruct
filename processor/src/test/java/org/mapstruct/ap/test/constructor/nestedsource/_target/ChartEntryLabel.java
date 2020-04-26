/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.nestedsource._target;

/**
 * @author Filip Hrisafov
 */
public class ChartEntryLabel {

    private final String name;
    private final String city;
    private final String recordedAt;

    public ChartEntryLabel(String name, String city, String recordedAt) {
        this.name = name;
        this.city = city;
        this.recordedAt = recordedAt;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getRecordedAt() {
        return recordedAt;
    }
}
