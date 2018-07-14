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
public class ExtendsBoundSource {

    private Stream<? extends Idea> elements;

    private List<? extends Idea> listElements;

    public Stream<? extends Idea> getElements() {
        return elements;
    }

    public void setElements(Stream<? extends Idea> elements) {
        this.elements = elements;
    }

    public List<? extends Idea> getListElements() {
        return listElements;
    }

    public void setListElements(List<? extends Idea> listElements) {
        this.listElements = listElements;
    }
}
