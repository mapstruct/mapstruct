/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

/**
 * Strategy for dealing with null source values.
 *
 * <b>Note:</b> This strategy is not in effect when a specific source presence check method is defined
 * in the service provider interface (SPI).
 *  <p>
 *  <b>Note</b>: some types of mappings (collections, maps), in which MapStruct is instructed to use a getter or adder
 *  as target accessor see {@link CollectionMappingStrategy}, MapStruct will always generate a source property null
 *  check, regardless the value of the {@link NullValueCheckStrategy} to avoid addition of {@code null} to the target
 *  collection or map.
 *
 * @author Sean Huang
 */
public enum NullValueCheckStrategy {

    /**
     * This option includes a null check as well as an empty check for Optional type. When:
     * <br>
     * <br>
     * <ol>
     *   <li>a source value is directly assigned to a target</li>
     *   <li>a source value assigned to a target by calling a type conversion on the target first</li>
     * </ol>
     * <br>
     * <b>NOTE:</b> mapping methods (generated or hand written) are excluded from this null check. They are intended to
     * handle a null source value as 'valid' input.
     *
     */
    ON_IMPLICIT_CONVERSION,

    /**
     * This option always includes a null check as well as an empty check for Optional type.
     */
    ALWAYS;

}
