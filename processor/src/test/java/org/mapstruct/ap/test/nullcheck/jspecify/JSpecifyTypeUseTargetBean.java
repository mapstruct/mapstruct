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
public class JSpecifyTypeUseTargetBean {

    private String @Nullable[] nullableArray;
    private @Nullable String[] nullableArrayElements;
    private @Nullable JSpecifyTypeUseTargetElement[] nullableElementArray;
    private List<@Nullable JSpecifyTypeUseTargetElement> nullableListElements;
    private List<List<@Nullable JSpecifyTypeUseTargetElement>> nestedNullableListElements;

    public String @Nullable[] getNullableArray() {
        return nullableArray;
    }

    public void setNullableArray(String @Nullable[] nullableArray) {
        this.nullableArray = nullableArray;
    }

    public @Nullable String[] getNullableArrayElements() {
        return nullableArrayElements;
    }

    public void setNullableArrayElements(String[] nullableArrayElements) {
        this.nullableArrayElements = nullableArrayElements;
    }

    public List<@Nullable JSpecifyTypeUseTargetElement> getNullableListElements() {
        return nullableListElements;
    }

    public void setNullableListElements(List<@Nullable JSpecifyTypeUseTargetElement> nullableListElements) {
        this.nullableListElements = nullableListElements;
    }

    public @Nullable JSpecifyTypeUseTargetElement[] getNullableElementArray() {
        return nullableElementArray;
    }

    public void setNullableElementArray(@Nullable JSpecifyTypeUseTargetElement[] nullableElementArray) {
        this.nullableElementArray = nullableElementArray;
    }

    public List<List<@Nullable JSpecifyTypeUseTargetElement>> getNestedNullableListElements() {
        return nestedNullableListElements;
    }

    public void setNestedNullableListElements(
        List<List<@Nullable JSpecifyTypeUseTargetElement>> nestedNullableListElements
    ) {
        this.nestedNullableListElements = nestedNullableListElements;
    }
}
