/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Target {

    //CHECKSTYLE:OFF
    public List<String> publicStringList;
    //CHECKSTYLE:On

    private List<String> stringList;
    private List<String> otherStringList;
    private ArrayList<String> stringArrayList;

    private Set<String> stringSet;
    private HashSet<String> stringHashSet;

    private Collection<String> stringCollection;

    private Collection<Integer> integerCollection;

    private Set<String> anotherStringSet;

    private Set<String> colours;

    private Map<String, Long> stringLongMap;
    private Map<String, Long> otherStringLongMap;

    private List<String> stringListNoSetter;

    private List<String> stringListNoSetter2;

    @SuppressWarnings( "rawtypes" )
    private Set set;

    private EnumSet<Colour> enumSet;

    private StringHolderArrayList nonGenericStringList;

    private StringHolderToLongMap nonGenericMapStringtoLong;

    public Target() {
        otherStringLongMap = new HashMap<>();
        otherStringLongMap.put( "not-present-after-mapping", 42L );

        otherStringList = new ArrayList<>();
        otherStringList.add( "not-present-after-mapping" );
    }

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

    public Collection<Integer> getIntegerCollection() {
        return integerCollection;
    }

    public void setIntegerCollection(Collection<Integer> integerCollection) {
        this.integerCollection = integerCollection;
    }

    @SuppressWarnings("rawtypes")
    public Set getSet() {
        return set;
    }

    @SuppressWarnings("rawtypes")
    public void setSet(Set set) {
        this.set = set;
    }

    public Set<String> getAnotherStringSet() {
        return anotherStringSet;
    }

    public void setAnotherStringSet(Set<String> anotherStringSet) {
        this.anotherStringSet = anotherStringSet;
    }

    public void setColours(Set<String> colours) {
        this.colours = colours;
    }

    public Set<String> getColours() {
        return colours;
    }

    public Map<String, Long> getStringLongMap() {
        return stringLongMap;
    }

    public void setStringLongMap(Map<String, Long> stringLongMap) {
        this.stringLongMap = stringLongMap;
    }

    public List<String> getStringListNoSetter() {
        if ( stringListNoSetter == null ) {
            stringListNoSetter = new ArrayList<>();
        }
        return stringListNoSetter;
    }

    public List<String> getStringListNoSetter2() {
        if ( stringListNoSetter2 == null ) {
            stringListNoSetter2 = new ArrayList<>();
        }
        return stringListNoSetter2;
    }

    public Map<String, Long> getOtherStringLongMap() {
        return otherStringLongMap;
    }

    public void setOtherStringLongMap(Map<String, Long> otherStringLongMap) {
        this.otherStringLongMap = otherStringLongMap;
    }

    public List<String> getOtherStringList() {
        return otherStringList;
    }

    public void setOtherStringList(List<String> otherStringList) {
        this.otherStringList = otherStringList;
    }

    public EnumSet<Colour> getEnumSet() {
        return enumSet;
    }

    public void setEnumSet(EnumSet<Colour> enumSet) {
        this.enumSet = enumSet;
    }

    public StringHolderArrayList getNonGenericStringList() {
        return nonGenericStringList;
    }

    public void setNonGenericStringList(StringHolderArrayList nonGenericStringList) {
        this.nonGenericStringList = nonGenericStringList;
    }

    public StringHolderToLongMap getNonGenericMapStringtoLong() {
        return nonGenericMapStringtoLong;
    }

    public void setNonGenericMapStringtoLong(StringHolderToLongMap nonGenericMapStringtoLong) {
        this.nonGenericMapStringtoLong = nonGenericMapStringtoLong;
    }
}
