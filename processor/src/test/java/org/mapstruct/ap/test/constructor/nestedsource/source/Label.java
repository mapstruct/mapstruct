/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.nestedsource.source;

/**
 * @author Filip Hrisafov
 */
public class Label {

    private final String name;
    private final Studio studio;

    public Label(String name, Studio studio) {
        this.name = name;
        this.studio = studio;
    }

    public String getName() {
        return name;
    }

    public Studio getStudio() {
        return studio;
    }

}
