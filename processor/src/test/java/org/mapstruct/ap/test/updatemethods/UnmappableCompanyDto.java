/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods;

/**
 *
 * @author Dmytro Polovinkin
 */
public class UnmappableCompanyDto {

    private String name;
    private UnmappableDepartmentDto department;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UnmappableDepartmentDto getDepartment() {
        return department;
    }

    public void setDepartment(UnmappableDepartmentDto department) {
        this.department = department;
    }

}
