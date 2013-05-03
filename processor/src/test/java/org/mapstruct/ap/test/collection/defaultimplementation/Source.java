/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.collection.defaultimplementation;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Source {

    private List<SourceFoo> fooList;

    private Set<SourceFoo> fooSet;

    private Collection<SourceFoo> fooCollection;

    private Iterable<SourceFoo> fooIterable;

    public List<SourceFoo> getFooList() {
        return fooList;
    }

    public void setFooList(List<SourceFoo> fooList) {
        this.fooList = fooList;
    }

    public Set<SourceFoo> getFooSet() {
        return fooSet;
    }

    public void setFooSet(Set<SourceFoo> fooSet) {
        this.fooSet = fooSet;
    }

    public Collection<SourceFoo> getFooCollection() {
        return fooCollection;
    }

    public void setFooCollection(Collection<SourceFoo> fooCollection) {
        this.fooCollection = fooCollection;
    }

    public Iterable<SourceFoo> getFooIterable() {
        return fooIterable;
    }

    public void setFooIterable(Iterable<SourceFoo> fooIterable) {
        this.fooIterable = fooIterable;
    }
}
