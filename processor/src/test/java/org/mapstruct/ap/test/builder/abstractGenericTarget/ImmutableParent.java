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
