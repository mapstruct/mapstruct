/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.defaultimplementation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filip Hrisafov
 *
 */
public class NoSetterTarget {
    private List<String> listValues = new ArrayList<String>();

    public List<String> getListValues() {
        return listValues;
    }
}
