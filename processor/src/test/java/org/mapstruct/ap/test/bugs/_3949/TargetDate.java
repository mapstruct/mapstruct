/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3949;

import java.time.LocalDate;

public class TargetDate implements TargetDateInterface {
    private LocalDate date = LocalDate.now();
    private String string = "";

    @Override
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public void setDate(String date) {
        this.string = date;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public String getString() {
        return string;
    }
}
