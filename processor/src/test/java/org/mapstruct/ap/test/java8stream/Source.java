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
package org.mapstruct.ap.test.java8stream;

import java.util.stream.Stream;

public class Source {

    private Stream<String> stringStream;
    private Stream<String> stringArrayStream;

    private Stream<String> stringStreamToSet;

    private Stream<Integer> integerStream;

    private Stream<Integer> anotherIntegerStream;

    private Stream<Colour> colours;

    private Stream<String> stringStream2;

    private Stream<String> stringStream3;

    public Stream<String> getStringStream() {
        return stringStream;
    }

    public void setStringStream(Stream<String> stringStream) {
        this.stringStream = stringStream;
    }

    public Stream<String> getStringArrayStream() {
        return stringArrayStream;
    }

    public void setStringArrayStream(Stream<String> stringArrayStream) {
        this.stringArrayStream = stringArrayStream;
    }

    public Stream<String> getStringStreamToSet() {
        return stringStreamToSet;
    }

    public void setStringStreamToSet(Stream<String> stringStreamToSet) {
        this.stringStreamToSet = stringStreamToSet;
    }

    public Stream<Integer> getIntegerStream() {
        return integerStream;
    }

    public void setIntegerStream(Stream<Integer> integerStream) {
        this.integerStream = integerStream;
    }

    public Stream<Integer> getAnotherIntegerStream() {
        return anotherIntegerStream;
    }

    public void setAnotherIntegerStream(Stream<Integer> anotherIntegerStream) {
        this.anotherIntegerStream = anotherIntegerStream;
    }

    public Stream<Colour> getColours() {
        return colours;
    }

    public void setColours(Stream<Colour> colours) {
        this.colours = colours;
    }

    public Stream<String> getStringStream2() {
        return stringStream2;
    }

    public void setStringStream2(Stream<String> stringStream2) {
        this.stringStream2 = stringStream2;
    }

    public Stream<String> getStringStream3() {
        return stringStream3;
    }

    public void setStringStream3(Stream<String> stringStream3) {
        this.stringStream3 = stringStream3;
    }
}
