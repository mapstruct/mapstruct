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

import java.util.Collection;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Stream;

/**
 * @author Filip Hrisafov
 */
public class Target {

    private Stream<Integer> ints;

    private Stream<Integer> targetStream;

    private List<String> stringStream;

    private Collection<String> stringCollection;

    private Set<Integer> integerSet;

    private Iterable<Integer> integerIterable;

    private SortedSet<Integer> sortedSet;

    private NavigableSet<Integer> navigableSet;

    private List<String> intToStringStream;

    private Integer[] stringArrayStream;

    private List<TargetElement> targetElements;

    public Stream<Integer> getInts() {
        return ints;
    }

    public void setInts(Stream<Integer> ints) {
        this.ints = ints;
    }

    public Stream<Integer> getTargetStream() {
        return targetStream;
    }

    public void setTargetStream(Stream<Integer> targetStream) {
        this.targetStream = targetStream;
    }

    public List<String> getStringStream() {
        return stringStream;
    }

    public void setStringStream(List<String> stringStream) {
        this.stringStream = stringStream;
    }

    public Collection<String> getStringCollection() {
        return stringCollection;
    }

    public void setStringCollection(Collection<String> stringCollection) {
        this.stringCollection = stringCollection;
    }

    public Set<Integer> getIntegerSet() {
        return integerSet;
    }

    public void setIntegerSet(Set<Integer> integerSet) {
        this.integerSet = integerSet;
    }

    public Iterable<Integer> getIntegerIterable() {
        return integerIterable;
    }

    public void setIntegerIterable(Iterable<Integer> integerIterable) {
        this.integerIterable = integerIterable;
    }

    public SortedSet<Integer> getSortedSet() {
        return sortedSet;
    }

    public void setSortedSet(SortedSet<Integer> sortedSet) {
        this.sortedSet = sortedSet;
    }

    public NavigableSet<Integer> getNavigableSet() {
        return navigableSet;
    }

    public void setNavigableSet(NavigableSet<Integer> navigableSet) {
        this.navigableSet = navigableSet;
    }

    public List<String> getIntToStringStream() {
        return intToStringStream;
    }

    public void setIntToStringStream(List<String> intToStringStream) {
        this.intToStringStream = intToStringStream;
    }

    public Integer[] getStringArrayStream() {
        return stringArrayStream;
    }

    public void setStringArrayStream(Integer[] stringArrayStream) {
        this.stringArrayStream = stringArrayStream;
    }

    public List<TargetElement> getTargetElements() {
        return targetElements;
    }

    public void setTargetElements(List<TargetElement> targetElements) {
        this.targetElements = targetElements;
    }
}
