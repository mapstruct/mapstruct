/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._913;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Sjaak Derksen
 */
public class DomainWithoutSetter {

    private final Set<String> strings = new HashSet<String>();
    private final Set<Long> longs = new HashSet<Long>();
    private final Set<String> stringsInitialized = new HashSet<String>();
    private final Set<Long> longsInitialized = new HashSet<Long>();
    private final List<String> stringsWithDefault = new ArrayList<String>();

    public Set<String> getStrings() {
        return strings;
    }

    public Set<Long> getLongs() {
        return longs;
    }

    public Set<String> getStringsInitialized() {
        return stringsInitialized;
    }

    public Set<Long> getLongsInitialized() {
        return longsInitialized;
    }

    public List<String> getStringsWithDefault() {
        return stringsWithDefault;
    }
}
