/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.abstractGenericTarget;

public class ImmutableParent implements Parent<ImmutableChild> {
    private final int count;
    private final ImmutableChild child;
    private final Child nonGenericChild;

    public ImmutableParent(Builder builder) {
        this.count = builder.count;
        this.child = builder.child;
        this.nonGenericChild = builder.nonGenericChild;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Child getNonGenericChild() {
        return nonGenericChild;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public ImmutableChild getChild() {
        return child;
    }

    public static class Builder {
        private int count;
        private ImmutableChild child;
        private Child nonGenericChild;

        public Builder count(int count) {
            this.count = count;
            return this;
        }

        public Builder nonGenericChild(Child nonGenericChild) {
            this.nonGenericChild = nonGenericChild;
            return this;
        }

        public Builder child(ImmutableChild child) {
            this.child = child;
            return this;
        }

        public ImmutableParent build() {
            return new ImmutableParent( this );
        }

    }
}
