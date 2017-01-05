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
package org.mapstruct.ap.test.java8stream.base;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Filip Hrisafov
 */
public class Source {

    private List<Integer> ints;

    private Stream<Integer> stream;

    private Stream<String> stringStream;

    private Stream<String> stringCollection;

    private Stream<Integer> integerSet;

    private Stream<Integer> integerIterable;

    private Stream<Integer> sortedSet;

    private Stream<Integer> navigableSet;

    private Stream<Integer> intToStringStream;

    private Stream<String> stringArrayStream;

    private Stream<SourceElement> sourceElements;

    public List<Integer> getInts() {
        return ints;
    }

    public void setInts(List<Integer> ints) {
        this.ints = ints;
    }

    public Stream<Integer> getStream() {
        return stream;
    }

    public void setStream(Stream<Integer> stream) {
        this.stream = stream;
    }

    public Stream<String> getStringStream() {
        return stringStream;
    }

    public void setStringStream(Stream<String> stringStream) {
        this.stringStream = stringStream;
    }

    public Stream<String> getStringCollection() {
        return stringCollection;
    }

    public void setStringCollection(Stream<String> stringCollection) {
        this.stringCollection = stringCollection;
    }

    public Stream<Integer> getIntegerSet() {
        return integerSet;
    }

    public void setIntegerSet(Stream<Integer> integerSet) {
        this.integerSet = integerSet;
    }

    public Stream<Integer> getIntegerIterable() {
        return integerIterable;
    }

    public void setIntegerIterable(Stream<Integer> integerIterable) {
        this.integerIterable = integerIterable;
    }

    public Stream<Integer> getSortedSet() {
        return sortedSet;
    }

    public void setSortedSet(Stream<Integer> sortedSet) {
        this.sortedSet = sortedSet;
    }

    public Stream<Integer> getNavigableSet() {
        return navigableSet;
    }

    public void setNavigableSet(Stream<Integer> navigableSet) {
        this.navigableSet = navigableSet;
    }

    public Stream<Integer> getIntToStringStream() {
        return intToStringStream;
    }

    public void setIntToStringStream(Stream<Integer> intToStringStream) {
        this.intToStringStream = intToStringStream;
    }

    public Stream<String> getStringArrayStream() {
        return stringArrayStream;
    }

    public void setStringArrayStream(Stream<String> stringArrayStream) {
        this.stringArrayStream = stringArrayStream;
    }

    public Stream<SourceElement> getSourceElements() {
        return sourceElements;
    }

    public void setSourceElements(Stream<SourceElement> sourceElements) {
        this.sourceElements = sourceElements;
    }
}
