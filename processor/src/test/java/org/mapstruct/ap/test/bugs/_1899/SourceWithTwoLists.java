/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1899;

import java.util.List;

/**
 * @author Ruslan Mikhalev
 */
public class SourceWithTwoLists {

    private List<String> elements;
    private List<String> secondElements;

    public List<String> getElements() {
        return elements;
    }

    public void setElements(List<String> elements) {
        this.elements = elements;
    }

    public List<String> getSecondElements() {
        return secondElements;
    }

    public void setSecondElements(List<String> secondElements) {
        this.secondElements = secondElements;
    }
}
