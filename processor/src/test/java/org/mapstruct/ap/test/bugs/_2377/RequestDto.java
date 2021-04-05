/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2377;

import java.util.List;

/**
 * @author Filip Hrisafov
 */
public class RequestDto {

    private JsonNullable<String> name = JsonNullable.undefined();
    private JsonNullable<List<ChildRequestDto>> children = JsonNullable.undefined();

    public JsonNullable<String> getName() {
        return name;
    }

    public void setName(JsonNullable<String> name) {
        this.name = name;
    }

    public JsonNullable<List<ChildRequestDto>> getChildren() {
        return children;
    }

    public void setChildren(JsonNullable<List<ChildRequestDto>> children) {
        this.children = children;
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
