/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcomposition;

public class BoxDto {

    private String groupName;
    private String designation;
    private ShelveDto shelve;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public ShelveDto getShelve() {
        return shelve;
    }

    public void setShelve(ShelveDto shelve) {
        this.shelve = shelve;
    }
}
