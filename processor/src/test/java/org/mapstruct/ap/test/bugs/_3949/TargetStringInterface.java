package org.mapstruct.ap.test.bugs._3949;

import java.time.LocalDate;

public interface TargetStringInterface {
    void setDate(LocalDate date);

    void setDate(String date);

    String getDate();

    LocalDate getDateValue();
}
