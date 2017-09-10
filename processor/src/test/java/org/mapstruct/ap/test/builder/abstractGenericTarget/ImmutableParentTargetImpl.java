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

public class ImmutableParentTargetImpl implements AbstractParentTarget<ImmutableChildTargetImpl> {
    private final int count;
    private final ImmutableChildTargetImpl nested;
    private final AbstractChildTarget nonGenericizedNested;

    public ImmutableParentTargetImpl(Builder builder) {
        this.count = builder.count;
        this.nested = builder.nested;
        this.nonGenericizedNested = builder.nonGenericizedNested;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public AbstractChildTarget getNonGenericizedNested() {
        return nonGenericizedNested;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public ImmutableChildTargetImpl getNested() {
        return nested;
    }

    public static class Builder {
        private int count;
        private ImmutableChildTargetImpl nested;
        private AbstractChildTarget nonGenericizedNested;

        public Builder count(int count) {
            this.count = count;
            return this;
        }

        public Builder nonGenericizedNested(AbstractChildTarget nonGenericizedNested) {
            this.nonGenericizedNested = nonGenericizedNested;
            return this;
        }

        public Builder nested(ImmutableChildTargetImpl nested) {
            this.nested = nested;
            return this;
        }

        public ImmutableParentTargetImpl build() {
            return new ImmutableParentTargetImpl( this );
        }

    }
}
