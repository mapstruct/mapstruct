/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Target {

    private List<String> stringList;
    private ArrayList<String> stringArrayList;

    private Set<String> stringSet;

    private Collection<Integer> integerCollection;

    private Set<String> anotherStringSet;

    private Set<String> colours;

    private List<String> stringListNoSetter;

    private StringHolderArrayList nonGenericStringList;

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

    public Collection<Integer> getIntegerCollection() {
        return integerCollection;
    }

    public void setIntegerCollection(Collection<Integer> integerCollection) {
        this.integerCollection = integerCollection;
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

    public List<String> getStringListNoSetter() {
        if ( stringListNoSetter == null ) {
            stringListNoSetter = new ArrayList<>();
        }
        return stringListNoSetter;
    }

    public StringHolderArrayList getNonGenericStringList() {
        return nonGenericStringList;
    }

    public void setNonGenericStringList(StringHolderArrayList nonGenericStringList) {
        this.nonGenericStringList = nonGenericStringList;
    }
}
