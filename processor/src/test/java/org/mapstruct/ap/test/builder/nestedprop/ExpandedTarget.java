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
package org.mapstruct.ap.test.builder.nestedprop;

import org.mapstruct.MappedByBuilder;
import org.mapstruct.ap.test.builder.simple.SimpleImmutableTarget;

@MappedByBuilder( ExpandedTarget.Builder.class)
public class ExpandedTarget {
    private final int count;
    private final SimpleImmutableTarget first;
    private final SimpleImmutableTarget second;

    ExpandedTarget(Builder builder) {
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

    public SimpleImmutableTarget getFirst() {
        return first;
    }

    public SimpleImmutableTarget getSecond() {
        return second;
    }

    public static class Builder {
        private int count;
        private SimpleImmutableTarget first;
        private SimpleImmutableTarget second;

        public Builder count(int age) {
            this.count = count;
            return this;
        }

        public Builder first(SimpleImmutableTarget first) {
            this.first = first;
            return this;
        }

        public Builder second(SimpleImmutableTarget second) {
            this.second = second;
            return this;
        }

        public ExpandedTarget build() {
            return new ExpandedTarget(this);
        }
    }
}
