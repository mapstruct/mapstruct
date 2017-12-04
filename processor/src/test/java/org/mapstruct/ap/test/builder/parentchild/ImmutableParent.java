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
package org.mapstruct.ap.test.builder.parentchild;

import java.util.List;

public class ImmutableParent {
    private final int count;
    private final List<ImmutableChild> children;

    ImmutableParent(Builder builder) {
        this.count = builder.count;
        this.children = builder.children;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getCount() {
        return count;
    }

    public List<ImmutableChild> getChildren() {
        return children;
    }

    public static class Builder {
        private List<ImmutableChild> children;
        private int count;

        public Builder children(List<ImmutableChild> children) {
            this.children = children;
            return this;
        }

        public ImmutableParent build() {
            return new ImmutableParent( this );
        }

        public Builder count(int count) {
            this.count = count;
            return this;
        }
    }
}
