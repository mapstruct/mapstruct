/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2663;

/**
 * @author Filip Hrisafov
 */
public class RequestDto {

    private JsonNullable<String> name = JsonNullable.undefined();
    private JsonNullable<ChildRequestDto> child = JsonNullable.undefined();

    public JsonNullable<String> getName() {
        return name;
    }

    public void setName(JsonNullable<String> name) {
        this.name = name;
    }

    public JsonNullable<ChildRequestDto> getChild() {
        return child;
    }

    public void setChild(JsonNullable<ChildRequestDto> child) {
        this.child = child;
    }

    public static class ChildRequestDto {

        private JsonNullable<String> name = JsonNullable.undefined();

        public JsonNullable<String> getName() {
            return name;
        }

        public void setName(JsonNullable<String> name) {
            this.name = name;
        }
    }
}
