/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
