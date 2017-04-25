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
package org.mapstruct.ap.test.bugs._913;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Sjaak Derksen
 */
public class Domain {
    static final Set<String> DEFAULT_STRINGS = new HashSet<String>();
    static final Set<Long> DEFAULT_LONGS = new HashSet<Long>();

    private Set<String> strings = DEFAULT_STRINGS;
    private Set<Long> longs = DEFAULT_LONGS;
    private Set<String> stringsInitialized;
    private Set<Long> longsInitialized;
    private List<String> stringsWithDefault;

    public Set<String> getStrings() {
        return strings;
    }

    public void setStrings(Set<String> strings) {
        this.strings = strings;
    }

    public Set<Long> getLongs() {
        return longs;
    }

    public void setLongs(Set<Long> longs) {
        this.longs = longs;
    }

    public Set<String> getStringsInitialized() {
        return stringsInitialized;
    }

    public void setStringsInitialized(Set<String> stringsInitialized) {
        this.stringsInitialized = stringsInitialized;
    }

    public Set<Long> getLongsInitialized() {
        return longsInitialized;
    }

    public void setLongsInitialized(Set<Long> longsInitialized) {
        this.longsInitialized = longsInitialized;
    }

    public List<String> getStringsWithDefault() {
        return stringsWithDefault;
    }

    public void setStringsWithDefault(List<String> stringsWithDefault) {
        this.stringsWithDefault = stringsWithDefault;
    }

}
