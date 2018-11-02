/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.propertymapping;

public class Source {

    private UnmappableClass source;
    private UnmappableClass nameBasedSource;

    public UnmappableClass getSource() {
        return source;
    }

    public void setSource(UnmappableClass source) {
        this.source = source;
    }

    public UnmappableClass getNameBasedSource() {
        return nameBasedSource;
    }

    public void setNameBasedSource(UnmappableClass nameBasedSource) {
        this.nameBasedSource = nameBasedSource;
    }
}
