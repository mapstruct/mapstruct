package org.mapstruct.ap.test.builder;

import org.mapstruct.MappedByBuilder;

@MappedByBuilder(builderClass = ImmutableTarget.Builder.class)
public class ImmutableTarget {
    private final String name;
    private final int age;
    private final ImmutableParent pops;

    ImmutableTarget(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.pops = builder.pops;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ImmutableParent getPops() {
        return pops;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private String name;
        private int age;
        private ImmutableParent pops;

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder pops(ImmutableParent pops) {
            this.pops = pops;
            return this;
        }

        public ImmutableTarget build() {
            return new ImmutableTarget(this);
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }
    }
}
