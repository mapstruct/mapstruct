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
public class Target {

    private Set<String> properties;

    public void setProperties(Set<String> properties) {
        this.properties = properties;
    }
}
