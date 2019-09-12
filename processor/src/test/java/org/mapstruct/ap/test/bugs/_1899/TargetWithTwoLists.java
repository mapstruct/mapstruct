/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1899;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruslan Mikhalev
 */
public class TargetWithTwoLists {

    private List<String> elements = new ArrayList<>();
    private List<String> secondElements = new ArrayList<>();

    public List<String> getElements() {
        return elements;
    }

    public void setElements(List<String> elements) {
        this.elements = elements;
    }

    public void setSecondElements(List<String> secondElements) {
        throw new AssertionError(
            "setSecondElementList should not be called, the item should have been added via addSecondElement" );
    }

    public void addSecondElement(String element) {
        secondElements.add( element );
    }

    public List<String> getSecondElements() {
        return secondElements;
    }
}
