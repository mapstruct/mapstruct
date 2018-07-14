/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.forged;

import java.util.Map;
import java.util.Set;

public class Source {

    //CHECKSTYLE:OFF
    public Set<String> publicFooSet;
    //CHECKSTYLE:ON
    private Set<String> fooSet;
    private Set<String> fooSet2;

    //CHECKSTYLE:OFF
    public Map<String, Long> publicBarMap;
    //CHECKSTYLE:ON
    private Map<String, Long> barMap;
    private Map<String, Long> barMap2;

    public Set<String> getFooSet() {
        return fooSet;
    }

    public void setFooSet(Set<String> fooSet) {
        this.fooSet = fooSet;
    }

    public Map<String, Long> getBarMap() {
        return barMap;
    }

    public void setBarMap(Map<String, Long> barMap) {
        this.barMap = barMap;
    }

    public Set<String> getFooSet2() {
        return fooSet2;
    }

    public void setFooSet2( Set<String> fooSet2 ) {
        this.fooSet2 = fooSet2;
    }

    public Map<String, Long> getBarMap2() {
        return barMap2;
    }

    public void setBarMap2( Map<String, Long> barMap2 ) {
        this.barMap2 = barMap2;
    }
}
