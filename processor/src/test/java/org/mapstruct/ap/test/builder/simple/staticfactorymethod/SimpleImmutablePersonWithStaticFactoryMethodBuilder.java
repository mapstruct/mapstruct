/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.simple.staticfactorymethod;

import java.util.ArrayList;
import java.util.List;

public class SimpleImmutablePersonWithStaticFactoryMethodBuilder {
    private final String name;
    private final int age;
    private final String job;
    private final String city;
    private final String address;
    private final List<String> children;

    SimpleImmutablePersonWithStaticFactoryMethodBuilder(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.job = builder.job;
        this.city = builder.city;
        this.address = builder.address;
        this.children = new ArrayList<>( builder.children );
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getChildren() {
        return children;
    }

    public static class Builder {
        private String name;
        private int age;
        private String job;
        private String city;
        private String address;
        private List<String> children = new ArrayList<>();

        private Builder() {
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public SimpleImmutablePersonWithStaticFactoryMethodBuilder build() {
            return new SimpleImmutablePersonWithStaticFactoryMethodBuilder( this );
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder job(String job) {
            this.job = job;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public List<String> getChildren() {
            throw new UnsupportedOperationException( "This is just a marker method" );
        }

        public Builder addChild(String child) {
            this.children.add( child );
            return this;
        }
    }
}
