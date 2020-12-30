/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.frommap;

import java.util.List;

/**
 * @author Christian Kosmowski
 */
public class Target {

    // CHECKSTYLE:OFF
    public String normalInt;
    public String fieldWithMethods;
    public List<Integer> finalList;
    public List<Integer> normalList;
    public Integer fieldOnlyWithGetter;
    // CHECKSTYLE:ON

    public String getFieldWithMethods() {
        return fieldWithMethods + "23";
    }

    public void setFieldWithMethods(String fieldWithMethods) {
        this.fieldWithMethods = fieldWithMethods + "11";
    }
}
