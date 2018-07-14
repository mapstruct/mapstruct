/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.fields;

import java.util.List;

/**
 * @author Filip Hrisafov
 */
public class Target {

    // CHECKSTYLE:OFF
    public String finalInt;
    public String normalInt;
    public List<String> finalList;
    public List<String> normalList;
    public List<Integer> privateFinalList;
    public String fieldWithMethods;
    // CHECKSTYLE:ON

    public String getFieldWithMethods() {
        return fieldWithMethods + "23";
    }

    public void setFieldWithMethods(String fieldWithMethods) {
        this.fieldWithMethods = fieldWithMethods + "11";
    }
}
