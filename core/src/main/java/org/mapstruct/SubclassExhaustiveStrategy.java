/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

/**
 * Strategy for dealing with subclassMapping annotated methods.
 *
 * @since 1.5
 * @author Ben Zegveld
 */
public enum SubclassExhaustiveStrategy {

    /**
     * If there is no valid constructor or known method to create the return value of a with `@SubclassMapping`
     * annotated mapping then a compilation error will be thrown.
     */
    COMPILE_ERROR,

    /**
     * If there is no valid constructor or known method to create the return value of a with `@SubclassMapping`
     * annotated mapping then an {@link IllegalArgumentException} will be thrown if a call is made with a type for which
     * there is no {@link SubclassMapping} available.
     */
    RUNTIME_EXCEPTION;
}
