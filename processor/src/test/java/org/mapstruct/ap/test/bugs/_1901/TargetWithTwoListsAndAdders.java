/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1901;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruslan Mikhalev
 */
public class TargetWithTwoListsAndAdders {

    private List<String> elementList = new ArrayList<>();
    private List<String> secondElementList = new ArrayList<>();

    public List<String> getElementList() {
        return elementList;
    }

    public void setElementList(List<String> elements) {
        throw new AssertionError(
            "setElementList should not be called, the item should have been added via addElement" );
    }

    public void addElement(String element) {
        elementList.add( element );
    }

    public void setSecondElementList(List<String> secondElementList) {
        throw new AssertionError(
            "setSecondElementList should not be called, the item should have been added via addSecondElement" );
    }

    public void addSecondElement(String element) {
        secondElementList.add( element );
    }

    public List<String> getSecondElementList() {
        return secondElementList;
    }
}
