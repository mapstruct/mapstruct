/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.builder.simple;

import java.util.ArrayList;
import java.util.List;

public class SimpleImmutablePerson {
    private final String name;
    private final int age;
    private final String job;
    private final String city;
    private final String address;
    private final List<String> children;

    SimpleImmutablePerson(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.job = builder.job;
        this.city = builder.city;
        this.address = builder.address;
        this.children = new ArrayList<String>( builder.children );
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
        private List<String> children = new ArrayList<String>();

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public SimpleImmutablePerson build() {
            return new SimpleImmutablePerson( this );
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
