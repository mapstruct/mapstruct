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
package org.mapstruct.ap.test.builder.noop;

/**
 * @author Filip Hrisafov
 */
public class Person {

    private String name;

    public Person() {

    }

    public Person(Builder builder) {
        this.name = builder.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Builder create() {
        throw new UnsupportedOperationException( "Creating builder is not supported" );
    }

    public static class Builder {

        private String name;

        public Builder() {
            throw new UnsupportedOperationException( "Creating a builder is not supported" );
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Person create() {
            return new Person(this);
        }

    }
}
