/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks.ongeneratedmethods;

import java.util.List;

/**
 *
 * @author Sjaak Derksen
 */
public class CompanyDto {

    private List<EmployeeDto> employees;

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees( List<EmployeeDto> employees ) {
        this.employees = employees;
    }
}
