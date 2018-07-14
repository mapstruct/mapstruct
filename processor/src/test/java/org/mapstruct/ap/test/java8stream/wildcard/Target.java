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
public class Target {

    private List<Plan> elements;

    private Stream<Plan> listElements;

    public List<Plan> getElements() {
        return elements;
    }

    public void setElements(List<Plan> elements) {
        this.elements = elements;
    }

    public Stream<Plan> getListElements() {
        return listElements;
    }

    public void setListElements(Stream<Plan> listElements) {
        this.listElements = listElements;
    }
}
