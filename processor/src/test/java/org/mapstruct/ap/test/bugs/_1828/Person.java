/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1828;

public class Person {

    String name;
    private CompleteAddress completeAddress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompleteAddress getCompleteAddress() {
        return completeAddress;
    }

    public void setCompleteAddress(CompleteAddress completeAddress) {
        this.completeAddress = completeAddress;
    }
}
