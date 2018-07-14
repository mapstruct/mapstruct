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
public class Source {

    private Stream<Idea> elements;

    private List<Idea> listElements;

    public Stream<Idea> getElements() {
        return elements;
    }

    public void setElements(Stream<Idea> elements) {
        this.elements = elements;
    }

    public List<Idea> getListElements() {
        return listElements;
    }

    public void setListElements(List<Idea> listElements) {
        this.listElements = listElements;
    }
}
