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
