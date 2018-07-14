/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.parentchild;

import java.util.List;

public class ImmutableParent {
    private final int count;
    private final List<ImmutableChild> children;

    ImmutableParent(Builder builder) {
        this.count = builder.count;
        this.children = builder.children;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getCount() {
        return count;
    }

    public List<ImmutableChild> getChildren() {
        return children;
    }

    public static class Builder {
        private List<ImmutableChild> children;
        private int count;

        public Builder children(List<ImmutableChild> children) {
            this.children = children;
            return this;
        }

        public ImmutableParent build() {
            return new ImmutableParent( this );
        }

        public Builder count(int count) {
            this.count = count;
            return this;
        }
    }
}
