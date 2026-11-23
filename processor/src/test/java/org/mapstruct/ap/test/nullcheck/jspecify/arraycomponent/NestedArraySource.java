/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.arraycomponent;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class NestedArraySource {

    private final Child child;

    public NestedArraySource(Child child) {
        this.child = child;
    }

    public Child getChild() {
        return child;
    }

    @NullMarked
    public static class Child {

        private final String[] tags;

        public Child(String[] tags) {
            this.tags = tags;
        }

        public String[] getTags() {
            return tags;
        }
    }
}
