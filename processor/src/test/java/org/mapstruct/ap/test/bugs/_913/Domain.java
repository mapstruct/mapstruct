/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
    static final Set<String> DEFAULT_STRINGS = new HashSet<>();
    static final Set<Long> DEFAULT_LONGS = new HashSet<>();

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
