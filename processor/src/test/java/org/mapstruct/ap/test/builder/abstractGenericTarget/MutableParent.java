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

public class MutableParent implements Parent<ImmutableChild> {
    private int count;
    private ImmutableChild child;
    private Child nonGenericChild;

    @Override
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public ImmutableChild getChild() {
        return child;
    }

    public void setChild(ImmutableChild child) {
        this.child = child;
    }

    @Override
    public Child getNonGenericChild() {
        return nonGenericChild;
    }

    public void setNonGenericChild(Child nonGenericChild) {
        this.nonGenericChild = nonGenericChild;
    }
}
