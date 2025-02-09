/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.index;

import java.util.List;

public class Car {

    String make;
    List<Person> personList;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}
