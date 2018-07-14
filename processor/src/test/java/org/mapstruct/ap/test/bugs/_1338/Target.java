/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1338;

import java.util.ArrayList;

/**
 * @author Filip Hrisafov
 */
public class Target {

    private StringList properties = new StringList();

    public void addProperty(String property) {
        properties.add( property );
    }

    public void setProperties(StringList properties) {
        throw new IllegalStateException( "Setter is there just as a marker it should not be used" );
    }

    public StringList getProperties() {
        return properties;
    }

    public static class StringList extends ArrayList<String> {

        private StringList() {
            // Constructor is private so we get a compile error if we try to instantiate it
        }
    }
}
