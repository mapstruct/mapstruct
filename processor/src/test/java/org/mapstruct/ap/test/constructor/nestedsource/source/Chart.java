/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.nestedsource.source;

/**
 * @author Filip Hrisafov
 */
public class Chart {

    private final String type;
    private final String name;
    private final Song song;

    public Chart(String type, String name, Song song) {
        this.type = type;
        this.name = name;
        this.song = song;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Song getSong() {
        return song;
    }

}
