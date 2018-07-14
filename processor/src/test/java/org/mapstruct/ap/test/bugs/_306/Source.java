/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._306;

import java.util.Set;

public class Source {

    private Set<String> fooSet;

    public Set<String> getFooSet() {
        return fooSet;
    }

    public void setFooSet(Set<String> fooSet) {
        this.fooSet = fooSet;
    }
}
