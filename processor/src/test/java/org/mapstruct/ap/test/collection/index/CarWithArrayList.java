/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.index;

import java.util.ArrayList;

public class CarWithArrayList {

    String make;
    ArrayList<Person> personList;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public ArrayList<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(ArrayList<Person> personList) {
        this.personList = personList;
    }
}
