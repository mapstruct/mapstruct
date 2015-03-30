/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Source {

    private List<String> stringList;
    private List<String> otherStringList;
    private ArrayList<String> stringArrayList;

    private Set<String> stringSet;
    private HashSet<String> stringHashSet;

    private Collection<String> stringCollection;

    private List<Integer> integerList;

    private Set<Integer> integerSet;

    private Set<Integer> anotherIntegerSet;

    private Set<Colour> colours;

    private Map<String, Long> stringLongMap;

    private Map<String, Long> otherStringLongMap;

    private List<String> stringList2;

    private Set<String> stringSet2;

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public ArrayList<String> getStringArrayList() {
        return stringArrayList;
    }

    public void setStringArrayList(ArrayList<String> stringArrayList) {
        this.stringArrayList = stringArrayList;
    }

    public Set<String> getStringSet() {
        return stringSet;
    }

    public void setStringSet(Set<String> stringSet) {
        this.stringSet = stringSet;
    }

    public HashSet<String> getStringHashSet() {
        return stringHashSet;
    }

    public void setStringHashSet(HashSet<String> stringHashSet) {
        this.stringHashSet = stringHashSet;
    }

    public Collection<String> getStringCollection() {
        return stringCollection;
    }

    public void setStringCollection(Collection<String> stringCollection) {
        this.stringCollection = stringCollection;
    }

    public List<Integer> getIntegerList() {
        return integerList;
    }

    public void setIntegerList(List<Integer> integerList) {
        this.integerList = integerList;
    }

    public Set<Integer> getIntegerSet() {
        return integerSet;
    }

    public void setIntegerSet(Set<Integer> integerSet) {
        this.integerSet = integerSet;
    }

    public Set<Integer> getAnotherIntegerSet() {
        return anotherIntegerSet;
    }

    public void setAnotherIntegerSet(Set<Integer> anotherIntegerSet) {
        this.anotherIntegerSet = anotherIntegerSet;
    }

    public Set<Colour> getColours() {
        return colours;
    }

    public void setColours(Set<Colour> colours) {
        this.colours = colours;
    }

    public Map<String, Long> getStringLongMap() {
        return stringLongMap;
    }

    public void setStringLongMap(Map<String, Long> stringLongMap) {
        this.stringLongMap = stringLongMap;
    }

    public List<String> getStringList2() {
        return stringList2;
    }

    public void setStringList2(List<String> stringList2) {
        this.stringList2 = stringList2;
    }

    public List<String> getOtherStringList() {
        return otherStringList;
    }

    public void setOtherStringList(List<String> otherStringList) {
        this.otherStringList = otherStringList;
    }

    public Map<String, Long> getOtherStringLongMap() {
        return otherStringLongMap;
    }

    public void setOtherStringLongMap(Map<String, Long> otherStringLongMap) {
        this.otherStringLongMap = otherStringLongMap;
    }

    public Set<String> getStringSet2() {
        return stringSet2;
    }

    public void setStringSet2(Set<String> stringSet2) {
        this.stringSet2 = stringSet2;
    }

}
