/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1338;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filip Hrisafov
 */
public class Source {

    private List<String> properties;

    public void addProperty(String property) {
        if ( properties == null ) {
            properties = new ArrayList<String>();
        }
        properties.add( property );
    }

    public List<String> getProperties() {
        return properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
    }
}
