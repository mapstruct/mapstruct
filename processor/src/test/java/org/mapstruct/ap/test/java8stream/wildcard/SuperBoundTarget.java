/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.wildcard;

import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author Filip Hrisafov
 */
public class SuperBoundTarget {

    private List<? super Plan> elements;

    private Stream<? super Plan> listElements;

    public List<? super Plan> getElements() {
        return elements;
    }

    public void setElements(List<? super Plan> elements) {
        this.elements = elements;
    }

    public Stream<? super Plan> getListElements() {
        return listElements;
    }

    public void setListElements(Stream<? super Plan> listElements) {
        this.listElements = listElements;
    }
}
