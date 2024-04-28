/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

import org.mapstruct.Condition;
import org.mapstruct.ConditionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ConditionalMethodForSourceParameterAndPropertyMapper {

    ConditionalMethodForSourceParameterAndPropertyMapper INSTANCE = Mappers.getMapper(
        ConditionalMethodForSourceParameterAndPropertyMapper.class );

    Employee map(EmployeeDto employee);

    @Condition(appliesTo = {
        ConditionStrategy.SOURCE_PARAMETERS,
        ConditionStrategy.PROPERTIES
    })
    default boolean canMapEmployeeDto(EmployeeDto employee) {
        return employee != null && employee.getId() != null;
    }

    class EmployeeDto {

        private final String id;
        private final String name;
        private final EmployeeDto manager;

        public EmployeeDto(String id, String name) {
            this( id, name, null );
        }

        public EmployeeDto(String id, String name, EmployeeDto manager) {
            this.id = id;
            this.name = name;
            this.manager = manager;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public EmployeeDto getManager() {
            return manager;
        }
    }

    class Employee {

        private final String id;
        private final String name;
        private final Employee manager;

        public Employee(String id, String name, Employee manager) {
            this.id = id;
            this.name = name;
            this.manager = manager;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Employee getManager() {
            return manager;
        }
    }
}
