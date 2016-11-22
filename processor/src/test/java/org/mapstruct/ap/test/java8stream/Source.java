/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.java8stream;

import java.util.stream.Stream;

public class Source {

    private Stream<String> stringList;
    private Stream<String> stringArrayList;

    private Stream<String> stringSet;

    private Stream<Integer> integerList;

    private Stream<Integer> anotherIntegerSet;

    private Stream<Colour> colours;

    private Stream<String> stringList2;

    private Stream<String> stringList3;

    public Stream<String> getStringList() {
        return stringList;
    }

    public void setStringList(Stream<String> stringList) {
        this.stringList = stringList;
    }

    public Stream<String> getStringArrayList() {
        return stringArrayList;
    }

    public void setStringArrayList(Stream<String> stringArrayList) {
        this.stringArrayList = stringArrayList;
    }

    public Stream<String> getStringSet() {
        return stringSet;
    }

    public void setStringSet(Stream<String> stringSet) {
        this.stringSet = stringSet;
    }

    public Stream<Integer> getIntegerList() {
        return integerList;
    }

    public void setIntegerList(Stream<Integer> integerList) {
        this.integerList = integerList;
    }

    public Stream<Integer> getAnotherIntegerSet() {
        return anotherIntegerSet;
    }

    public void setAnotherIntegerSet(Stream<Integer> anotherIntegerSet) {
        this.anotherIntegerSet = anotherIntegerSet;
    }

    public Stream<Colour> getColours() {
        return colours;
    }

    public void setColours(Stream<Colour> colours) {
        this.colours = colours;
    }

    public Stream<String> getStringList2() {
        return stringList2;
    }

    public void setStringList2(Stream<String> stringList2) {
        this.stringList2 = stringList2;
    }

    public Stream<String> getStringList3() {
        return stringList3;
    }

    public void setStringList3(Stream<String> stringList3) {
        this.stringList3 = stringList3;
    }
}
