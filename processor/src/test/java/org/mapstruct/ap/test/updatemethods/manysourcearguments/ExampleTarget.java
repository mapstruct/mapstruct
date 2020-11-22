/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods.manysourcearguments;

import java.time.LocalDate;

public class ExampleTarget {

    private LocalDate birthday;

    private ExampleMember member;

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public ExampleMember getMember() {
        return member;
    }

    public void setMember(ExampleMember member) {
        this.member = member;
    }
}
