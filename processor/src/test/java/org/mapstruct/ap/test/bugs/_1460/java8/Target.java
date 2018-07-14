/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1460.java8;

import java.time.LocalDate;

public class Target {
    private LocalDate stringToJavaLocalDate;

    public LocalDate getStringToJavaLocalDate() {
        return stringToJavaLocalDate;
    }

    public void setStringToJavaLocalDate(LocalDate stringToJavaLocalDate) {
        this.stringToJavaLocalDate = stringToJavaLocalDate;
    }

}
