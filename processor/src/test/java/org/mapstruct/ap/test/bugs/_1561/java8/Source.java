/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1561.java8;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sebastian Haberey
 */
public class Source {

    private List<String> properties = new ArrayList<String>();

    public List<String> getProperties() {
        return properties;
    }

    public void addProperty(String property) {
        properties.add( property );
    }
}
