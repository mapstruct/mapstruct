/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.nullvaluecheckstrategy;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Sjaak Derksen
 */
public enum Stage {

    MAIN("Paul McCartney", "Ellie Goulding", "Disclosure", "Kaiser Chiefs", "Rammstein"),
    KLUB_C("James Blake", "Lost Frequencies"),
    THE_BARN("New Order", "Year and Years");

    private final List<String> artists;

    Stage(String... artist) {
        this.artists = Arrays.asList( artist );
    }

    public static Stage forArtist( String name ) {

        if ( name == null ) {
            throw new IllegalArgumentException();
        }

        for ( Stage value : Stage.values() ) {
            if ( value.artists.contains( name ) ) {
                return value;
            }
        }
        return null;
    }
}
