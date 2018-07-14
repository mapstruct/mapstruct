/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1359;

import java.util.Set;

/**
 * @author Filip Hrisafov
 */
public class Source {

    private Set<String> properties;

    public Source(Set<String> properties) {
        this.properties = properties;
    }

    public Set<String> getProperties() {
        return properties;
    }
}
