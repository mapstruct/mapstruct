/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1901;

import java.util.List;

/**
 * @author Ruslan Mikhalev
 */
public class SourceWithTwoLists {

    private List<String> elementList;
    private List<String> secondElementList;

    public List<String> getElementList() {
        return elementList;
    }

    public void setElementList(List<String> elements) {
        this.elementList = elements;
    }

    public List<String> getSecondElementList() {
        return secondElementList;
    }

    public void setSecondElementList(List<String> secondElementList) {
        this.secondElementList = secondElementList;
    }
}
