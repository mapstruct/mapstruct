package org.mapstruct.ap.test.builder;

import org.mapstruct.MappedByBuilder;

import java.util.List;

@MappedByBuilder(builderClass = ImmutableParent.Builder.class)
public class ImmutableParent {
    private final int count;
    private final List<ImmutableTarget> children;

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

    public List<ImmutableTarget> getChildren() {
        return children;
    }

    public static class Builder {
        private List<ImmutableTarget> children;
        private int count;

        public Builder children(List<ImmutableTarget> children) {
            this.children = children;
            return this;
        }

        public ImmutableParent build() {
            return new ImmutableParent(this);
        }

        public Builder count(int count) {
            this.count = count;
            return this;
        }
    }
}
