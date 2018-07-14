/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._849;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Sjaak Derksen
 */
public class Source {

    private List<Serializable> sourceList;

    public List<Serializable> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<Serializable> sourceList) {
        this.sourceList = sourceList;
    }
}
