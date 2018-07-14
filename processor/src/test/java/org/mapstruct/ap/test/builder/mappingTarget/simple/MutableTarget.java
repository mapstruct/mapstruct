/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.mappingTarget.simple;

public class MutableTarget {
    private String name;
    private int age;
    private String source;

    public MutableTarget() {
        this.source = "Empty constructor";
    }

    MutableTarget(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.source = "Builder";
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSource() {
        return source;
    }

    public static class Builder {
        private String name;
        private int age;

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public MutableTarget build() {
            return new MutableTarget( this );
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }
    }
}
