/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

/**
 * Indicates whether a generated Mapper implementation class should be declared {@code public} or not.
 *
 * @author Raimund Klein
 *
 * @since 1.7.0
 */
public enum ClassAccessibility {
    /**
     * Indicates that the generated Mapper should have the same modifier ({@code public} or none) as the annotated
     * class or interface.
     */
    LIKE_ABSTRACTION,
    /**
     * Indicates that the generated Mapoer should be declared {@code public}.
     */
    PUBLIC,
    /**
     * Indicates that the generated Mapper should have no visibility modifier ("package-private").
     */
    DEFAULT
}
