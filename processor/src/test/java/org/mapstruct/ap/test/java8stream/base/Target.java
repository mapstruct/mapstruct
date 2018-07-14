/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
