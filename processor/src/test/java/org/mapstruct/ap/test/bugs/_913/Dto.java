/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._913;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Sjaak Derksen
 */
public class Dto {

    private List<String> strings;
    private List<String> stringsInitialized = new ArrayList<String>( Arrays.asList( "5" ) );
    private List<String> stringsWithDefault;

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public List<String> getStringsInitialized() {
        return stringsInitialized;
    }

    public void setStringsInitialized(List<String> stringsInitialized) {
        this.stringsInitialized = stringsInitialized;
    }

    public List<String> getStringsWithDefault() {
        return stringsWithDefault;
    }

    public void setStringsWithDefault(List<String> stringsWithDefault) {
        this.stringsWithDefault = stringsWithDefault;
    }

}
