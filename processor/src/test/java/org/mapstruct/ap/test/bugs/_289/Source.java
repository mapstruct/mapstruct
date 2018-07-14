/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._289;

import java.util.Collection;

/**
 *
 * @author Sjaak Derksen
 */
public class Source {

    private Collection<SourceElement> collection;

    public Collection<SourceElement> getCollection() {
        return collection;
    }

    public void setCollection(Collection<SourceElement> collection) {
        this.collection = collection;
    }

}
