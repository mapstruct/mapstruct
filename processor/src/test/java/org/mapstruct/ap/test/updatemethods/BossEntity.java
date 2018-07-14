/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods;

/**
 * @author Andreas Gudian
 *
 */
public class BossEntity {
    private String name;
    private ConstructableDepartmentEntity department;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConstructableDepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(ConstructableDepartmentEntity department) {
        this.department = department;
    }
}
