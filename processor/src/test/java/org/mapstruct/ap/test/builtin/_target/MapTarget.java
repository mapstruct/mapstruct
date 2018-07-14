/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin._target;

import java.util.Map;

/**
 *
 * @author Sjaak Derksen
 */
public class MapTarget {

    // CHECKSTYLE:OFF
    public Map<String, String> publicExample;
    // CHECKSTYLE:ON

    private Map<String, String> example;

    public Map<String, String> getExample() {
        return example;
    }

    public void setExample( Map<String, String> example ) {
        this.example = example;
    }

}
