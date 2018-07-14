/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.defaultimplementation;

import java.util.stream.Stream;

/**
 * @author Filip Hrisafov
 *
 */
public class NoSetterSource {
    private Stream<String> listValues;

    public Stream<String> getListValues() {
        return listValues;
    }

    public void setListValues(Stream<String> listValues) {
        this.listValues = listValues;
    }
}
