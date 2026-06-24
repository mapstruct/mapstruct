/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

/**
 * Determines whether a generated Mapper implementation class will be declared {@code public} or not.
 *
 * @author Raimund Klein
 *
 * @since 1.7.0
 */
public enum ClassAccessibility {
    /**
     * The generated Mapper will have the same modifier ({@code public} or none) as the annotated class or interface.
     */
    DEFAULT,
    /**
     * The generated Mapper will be declared {@code public}.
     */
    PUBLIC,
    /**
     * The generated Mapper will have no visibility modifier ("package-private").
     */
    PACKAGE_PRIVATE
}
