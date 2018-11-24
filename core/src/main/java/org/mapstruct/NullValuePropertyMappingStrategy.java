/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

/**
 * Strategy for dealing with {@code null} or not present properties in the source bean. The
 * {@link NullValuePropertyMappingStrategy} can be defined on {@link MapperConfig}, {@link Mapper}, {@link BeanMapping}
 * and {@link Mapping}.
 * Precedence is arranged  in the reverse order. So {@link Mapping} will override {@link BeanMapping}, will
 * overide {@link Mapper}
 *
 * The enum only applies to update method: methods that update a pre-existing target (annotated with
 * {@code @}{@link MappingTarget}).
 *
 * @author Sjaak Derksen
 * @since 1.3
 */
public enum NullValuePropertyMappingStrategy {

    /**
     * If a source bean property equals {@code null} the target bean property will be set explicitly to {@code null}.
     */
    SET_TO_NULL,

    /**
     * If a source bean property equals {@code null} the target bean property will be set to its default value.
     */
    SET_TO_DEFAULT,

    /**
     * If a source bean property equals {@code null} the target bean property will be ignored and retain its
     * existing value.
     */
    IGNORE;
}
