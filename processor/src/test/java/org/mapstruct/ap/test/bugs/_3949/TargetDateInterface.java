package org.mapstruct.ap.test.bugs._3949;

import java.time.LocalDate;

public interface TargetDateInterface {
    void setDate(LocalDate date);

    void setDate(String date);

    LocalDate getDate();

    String getString();
}
