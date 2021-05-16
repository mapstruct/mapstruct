/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2437;

/**
 * @author Filip Hrisafov
 */
public class Phone {

    private final String number;

    public Phone(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}
