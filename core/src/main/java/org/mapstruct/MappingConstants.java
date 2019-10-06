/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

/**
 * Contains all constants defined in the mapping process.
 *
 * @author Sjaak Derksen
 */
public final class MappingConstants {

    private MappingConstants() {
    }

    /**
     * In an {@link ValueMapping} this represents a {@code null} source or target.
     */
    public static final String NULL = "<NULL>";

    /**
     * In an {@link ValueMapping} this represents any source that is not already mapped by either a defined mapping or
     * by means of name based mapping.
     *
     * NOTE: The value is only applicable to {@link ValueMapping#source()} and not to {@link ValueMapping#target()}.
     */
    public static final String ANY_REMAINING = "<ANY_REMAINING>";

    /**
     * In an {@link ValueMapping} this represents any source that is not already mapped by a defined mapping.
     *
     * NOTE: The value is only applicable to {@link ValueMapping#source()} and not to {@link ValueMapping#target()}.
     *
     */
    public static final String ANY_UNMAPPED = "<ANY_UNMAPPED>";

}
