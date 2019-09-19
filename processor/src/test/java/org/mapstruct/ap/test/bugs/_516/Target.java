/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._516;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sjaak Derksen
 */
public class Target {

    private List<String> elements;

    public List<String> getElements() {
        return elements;
    }

    public void setElements(List<String> elements) {
        this.elements = elements;
    }

    public void addElement(String element) {
        if ( elements == null ) {
            elements = new ArrayList<>();
        }
        elements.add( element );
    }
}
