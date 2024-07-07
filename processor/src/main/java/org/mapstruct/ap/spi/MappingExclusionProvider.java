/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import javax.lang.model.element.TypeElement;

import org.mapstruct.util.Experimental;

/**
 * A service provider interface that is used to control if MapStruct is allowed to generate automatic sub-mapping for
 * a given {@link TypeElement}.
 * <p>
 * When generating the implementation of a mapping method, MapStruct will apply the following routine for each
 * attribute pair in the source and target object:
 * <p>
 * <ul>
 *     <li>If source and target attribute have the same type, the value will be simply copied from source to target.
 *     If the attribute is a collection (e.g. a `List`) a copy of the collection will be set into the target
 *     attribute.</li>
 *     <li>If source and target attribute type differ, check whether there is a another mapping method which has the
 *     type of the source attribute as parameter type and the type of the target attribute as return type. If such a
 *     method exists it will be invoked in the generated mapping implementation.</li>
 *     <li>If no such method exists MapStruct will look whether a built-in conversion for the source and target type
 *     of the attribute exists. If this is the case, the generated mapping code will apply this conversion.</li>
 *     <li>If no such method was found MapStruct will try to generate an automatic sub-mapping method that will do
 *     the mapping between the source and target attributes</li>
 *     <li>If MapStruct could not create a name based mapping method an error will be raised at build time,
 *     indicating the non-mappable attribute and its path.</li>
 * </ul>
 * <p>
 * With this SPI the last step before raising an error can be controlled. i.e. A user can control whether MapStruct
 * is allowed to generate such automatic sub-mapping method (for the source or target type) or not.
 *
 * @author Filip Hrisafov
 * @since 1.2
 */
@Experimental("This SPI can have its signature changed in subsequent releases")
public interface MappingExclusionProvider {

    /**
     * Checks if MapStruct should not generate an automatic sub-mapping for the provided {@link TypeElement}, i.e.
     * MapStruct will not try to descent into this class and won't try to automatically map it with some other type.
     * The given {@code typeElement} will be excluded from the automatic sub-mapping generation
     *
     * @param typeElement that needs to be checked
     * @return {@code true} if MapStruct should exclude the provided {@link TypeElement} from an automatic sub-mapping
     */
    boolean isExcluded(TypeElement typeElement);
}
