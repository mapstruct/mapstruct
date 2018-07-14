/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.defaultimplementation;

import java.util.List;

public class Source {

    private List<SourceFoo> fooList;

    public List<SourceFoo> getFooList() {
        return fooList;
    }

    public void setFooList(List<SourceFoo> fooList) {
        this.fooList = fooList;
    }

}
