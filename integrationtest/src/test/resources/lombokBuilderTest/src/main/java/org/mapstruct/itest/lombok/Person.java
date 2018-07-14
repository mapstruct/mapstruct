/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.lombok;

import lombok.Builder;
import lombok.Getter;

//TODO make MapStruct DefaultBuilderProvider work with custom builder name
//@Builder(builderMethodName = "foo", buildMethodName = "create", builderClassName = "Builder")
@Builder(builderClassName = "Builder")
@Getter
public class Person {
    private final String name;
    private final int age;
    private final Address address;
}
