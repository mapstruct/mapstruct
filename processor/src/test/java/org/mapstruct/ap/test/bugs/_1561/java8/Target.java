/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1561.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Sebastian Haberey
 */
public class Target {

    private List<String> properties = new ArrayList<String>();

    public Stream<String> getProperties() {
        return properties.stream();
    }

    public void addProperty(String property) {
        properties.add( property );
    }
}
