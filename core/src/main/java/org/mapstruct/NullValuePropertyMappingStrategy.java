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
 * override {@link Mapper}
 *
 * The enum <b>only applies to update methods</b>: methods that update a pre-existing target (annotated with
 * {@code @}{@link MappingTarget}).
 *
 *  <p>
 *  <b>Note</b>: some types of mappings (collections, maps), in which MapStruct is instructed to use a getter or adder
 *  as target accessor see {@link CollectionMappingStrategy}, MapStruct will always generate a source property
 *  null check, regardless the value of the {@link NullValuePropertyMappingStrategy} to avoid addition of {@code null}
 *  to the target collection or map. Since the target is assumed to be initialised this strategy will not be applied.
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
     * <p>
     * This means:
     * <ol>
     * <li>For {@code List} MapStruct generates an {@code ArrayList}</li>
     * <li>For {@code Map} a {@code HashMap}</li>
     * <li>For arrays an empty array</li>
     * <li>For {@code String} {@code ""}</li>
     * <li>for primitive / boxed types a representation of {@code 0} or {@code false}</li>
     * <li>For all other objects an new instance is created, requiring an empty constructor.</li>
     * </ol>
     * <p>
     * Make sure that a {@link Mapping#defaultValue()} is defined if no empty constructor is available on
     * the default value.
     */
    SET_TO_DEFAULT,

    /**
     * If a source bean property equals {@code null} the target bean property will be ignored and retain its
     * existing value.
     */
    IGNORE;
}
