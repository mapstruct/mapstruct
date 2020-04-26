/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.nestedsource.source;

import java.util.List;

/**
 * @author Filip Hrisafov
 */
public class Song {

    private final Artist artist;
    private final String title;
    private final List<Integer> positions;

    public Song(Artist artist, String title, List<Integer> positions) {
        this.artist = artist;
        this.title = title;
        this.positions = positions;
    }

    public Artist getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public List<Integer> getPositions() {
        return positions;
    }

}
