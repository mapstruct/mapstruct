/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

import org.mapstruct.Mapper;
import org.mapstruct.SourceParameterCondition;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ConditionalMethodForSourceBeanMapper {

    ConditionalMethodForSourceBeanMapper INSTANCE = Mappers.getMapper( ConditionalMethodForSourceBeanMapper.class );

    Employee map(EmployeeDto employee);

    @SourceParameterCondition
    default boolean canMapEmployeeDto(EmployeeDto employee) {
        return employee != null && employee.getId() != null;
    }

    class EmployeeDto {

        private final String id;
        private final String name;

        public EmployeeDto(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    class Employee {

        private final String id;
        private final String name;

        public Employee(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
