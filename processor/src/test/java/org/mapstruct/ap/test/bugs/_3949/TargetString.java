package org.mapstruct.ap.test.bugs._3949;

import java.time.LocalDate;

public class TargetString implements TargetStringInterface {
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
    public String getDate() {
        return string;
    }

    @Override
    public LocalDate getDateValue() {
        return date;
    }

}
