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
