/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.immutables;

import java.util.Objects;

public class TopLevelDto {

    private ChildDto child;

    public org.mapstruct.itest.immutables.TopLevelDto.ChildDto getChild() {
        return child;
    }

    public TopLevelDto setChild(org.mapstruct.itest.immutables.TopLevelDto.ChildDto child) {
        this.child = child;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopLevelDto that = (TopLevelDto) o;
        return Objects.equals(child, that.child);
    }

    @Override
    public int hashCode() {
        return Objects.hash(child);
    }

    public static class ChildDto {
        private String string;

        public String getString() {
            return string;
        }

        public ChildDto setString(String string) {
            this.string = string;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ChildDto childDto = (ChildDto) o;
            return Objects.equals(string, childDto.string);
        }

        @Override
        public int hashCode() {
            return Objects.hash(string);
        }
    }
}
