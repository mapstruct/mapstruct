/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods;

/**
 *
 * @author Sjaak Derksen
 */
public class DepartmentEntityFactory {

    public DepartmentEntity createDepartmentEntity() {
        return new DepartmentEntity(5);
    }
}
