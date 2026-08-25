/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.List;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class JSpecifyTypeUseSourceBean {

    private String @Nullable[] nullableArray;
    private @Nullable String[] nullableArrayElements;
    private @Nullable JSpecifyTypeUseSourceElement[] nullableElementArray;
    private List<@Nullable JSpecifyTypeUseSourceElement> nullableListElements;
    private List<List<@Nullable JSpecifyTypeUseSourceElement>> nestedNullableListElements;

    public String @Nullable[] getNullableArray() {
        return nullableArray;
    }

    public void setNullableArray(String @Nullable[] nullableArray) {
        this.nullableArray = nullableArray;
    }

    public @Nullable String[] getNullableArrayElements() {
        return nullableArrayElements;
    }

    public void setNullableArrayElements(@Nullable String[] nullableArrayElements) {
        this.nullableArrayElements = nullableArrayElements;
    }

    public @Nullable JSpecifyTypeUseSourceElement[] getNullableElementArray() {
        return nullableElementArray;
    }

    public void setNullableElementArray(@Nullable JSpecifyTypeUseSourceElement[] nullableElementArray) {
        this.nullableElementArray = nullableElementArray;
    }

    public List<@Nullable JSpecifyTypeUseSourceElement> getNullableListElements() {
        return nullableListElements;
    }

    public void setNullableListElements(List<@Nullable JSpecifyTypeUseSourceElement> nullableListElements) {
        this.nullableListElements = nullableListElements;
    }

    public List<List<@Nullable JSpecifyTypeUseSourceElement>> getNestedNullableListElements() {
        return nestedNullableListElements;
    }

    public void setNestedNullableListElements(
        List<List<@Nullable JSpecifyTypeUseSourceElement>> nestedNullableListElements
    ) {
        this.nestedNullableListElements = nestedNullableListElements;
    }
}
