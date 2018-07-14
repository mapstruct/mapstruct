/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._955.dto;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sjaak Derksen
 */
public class Car {
    private Map<String, Person> persons = new HashMap<String, Person>();

    public Map<String, Person> getPersons() {
        return persons;
    }

    public void setPersons(Map<String, Person> persons) {
        this.persons = persons;
    }

}
