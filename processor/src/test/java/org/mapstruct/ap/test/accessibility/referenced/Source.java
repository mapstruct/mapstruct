/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.accessibility.referenced;

/**
 *
 * @author Sjaak Derksen
 */
public class Source {

    private ReferencedSource referencedSource;

    public ReferencedSource getReferencedSource() {
        return referencedSource;
    }

    public void setReferencedSource( ReferencedSource referencedSource ) {
        this.referencedSource = referencedSource;
    }

}
