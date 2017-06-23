package org.mapstruct.ap.test.builder;

import org.mapstruct.MappedByBuilder;

@MappedByBuilder(builderClass = ImmutableFlattened.Builder.class)
public class ImmutableFlattened {
    private final int count;
    private final ImmutableTarget first;
    private final ImmutableTarget second;

    ImmutableFlattened(Builder builder) {
        this.count = builder.count;
        this.first = builder.first;
        this.second = builder.second;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getCount() {
        return count;
    }

    public ImmutableTarget getFirst() {
        return first;
    }

    public ImmutableTarget getSecond() {
        return second;
    }

    public static class Builder {
        private int count;
        private ImmutableTarget first;
        private ImmutableTarget second;

        public Builder count(int age) {
            this.count = count;
            return this;
        }

        public Builder first(ImmutableTarget first) {
            this.first = first;
            return this;
        }

        public Builder second(ImmutableTarget second) {
            this.second = second;
            return this;
        }

        public ImmutableFlattened build() {
            return new ImmutableFlattened(this);
        }
    }
}
