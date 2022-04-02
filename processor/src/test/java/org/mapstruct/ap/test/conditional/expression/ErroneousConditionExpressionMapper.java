/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.expression;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.conditional.EmployeeDto;
import org.mapstruct.ap.test.conditional.basic.BasicEmployee;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ErroneousConditionExpressionMapper {

    @Mapping(target = "name", conditionExpression = "!employee.getName().isEmpty()")
    BasicEmployee map(EmployeeDto employee);

    @Mapping(target = "name", conditionExpression = "java(true)", constant = "test")
    BasicEmployee mapConstant(EmployeeDto employee);

    @Mapping(target = "name", conditionExpression = "java(true)", expression = "java(\"test\")")
    BasicEmployee mapExpression(EmployeeDto employee);
}
