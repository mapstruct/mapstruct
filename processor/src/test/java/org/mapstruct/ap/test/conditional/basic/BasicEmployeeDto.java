/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

/**
 * @author Filip Hrisafov
 */
public class BasicEmployeeDto {

    private final String name;
    private final String strategy;

    public BasicEmployeeDto(String name) {
        this( name, "default" );
    }

    public BasicEmployeeDto(String name, String strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    public String getName() {
        return name;
    }

    public String getStrategy() {
        return strategy;
    }
}
