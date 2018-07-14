/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Dmytro Polovinkin
 */
public class UnmappableDepartmentDto {

    private String name;
    private List<EmployeeDto> workers;
    private Map<SecretaryDto, EmployeeDto> secretaryToEmployee;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EmployeeDto> getWorkers() {
        return workers;
    }

    public void setWorkers(List<EmployeeDto> employees) {
        this.workers = employees;
    }

    public Map<SecretaryDto, EmployeeDto> getSecretaryToEmployee() {
        return secretaryToEmployee;
    }

    public void setSecretaryToEmployee(Map<SecretaryDto, EmployeeDto> secretaryToEmployee) {
        this.secretaryToEmployee = secretaryToEmployee;
    }

}
