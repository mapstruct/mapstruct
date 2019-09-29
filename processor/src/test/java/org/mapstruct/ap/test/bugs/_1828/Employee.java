/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1828;

public class Employee {

    private String name;
    private GeneralAddress generalAddress;
    private SpecialAddress specialAddress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeneralAddress getGeneralAddress() {
        return generalAddress;
    }

    public void setGeneralAddress(GeneralAddress generalAddress) {
        this.generalAddress = generalAddress;
    }

    public SpecialAddress getSpecialAddress() {
        return specialAddress;
    }

    public void setSpecialAddress(SpecialAddress specialAddress) {
        this.specialAddress = specialAddress;
    }
}
