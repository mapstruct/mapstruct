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
 * @author Sjaak Derksen
 */
public class DepartmentEntity {

    private String name;
    private List<EmployeeEntity> employees;
    private Map<SecretaryEntity, EmployeeEntity> secretaryToEmployee;

    public DepartmentEntity(Integer justAnArgToAvoidConstruction) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EmployeeEntity> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeEntity> employees) {
        this.employees = employees;
    }

    public Map<SecretaryEntity, EmployeeEntity> getSecretaryToEmployee() {
        return secretaryToEmployee;
    }

    public void setSecretaryToEmployee(Map<SecretaryEntity, EmployeeEntity> secretaryToEmployee) {
        this.secretaryToEmployee = secretaryToEmployee;
    }

}
