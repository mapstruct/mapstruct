/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.nestedsource.source;

/**
 * @author Filip Hrisafov
 */
public class Artist {

    private final String name;
    private final Label label;

    public Artist(String name, Label label) {
        this.name = name;
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public Label getLabel() {
        return label;
    }

}
