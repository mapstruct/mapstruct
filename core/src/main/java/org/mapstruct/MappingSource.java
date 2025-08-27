/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a parameter as a source parameter, providing fine-grained control over mapping behavior.
 * This annotation controls how parameters are handled during the mapping process.
 * <p>
 * Key features:
 * <ul>
 * <li>When combined with {@link Context}, allows the annotated parameter to be used as both
 * a context parameter and a source parameter simultaneously</li>
 * <li>Precisely controls whether a parameter participates in implicit mapping (whether its properties
 * are automatically mapped to the target object)</li>
 * <li>Can mark a parameter as primary to automatically resolve property conflicts in multi-source scenarios</li>
 * </ul>
 * <p>
 * Standard mapping behavior (without this annotation):
 * <ul>
 * <li>All Bean-type parameters have their properties automatically used for implicit mapping</li>
 * <li>Map parameters in multi-source scenarios don't have their entries automatically used for implicit mapping</li>
 * </ul>
 *
 * @since 1.6.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.CLASS)
public @interface MappingSource {

    /**
     * Controls whether this parameter participates in implicit mapping.
     * The effect depends on the parameter type:
     * <p>
     * <b>Bean/Collection parameters:</b>
     * <ul>
     * <li>true : Properties automatically participate in implicit mapping. This maintains the same behavior
     * as not applying the annotation at all, since Bean properties are implicitly mapped by default.</li>
     * <li>false: Properties do not automatically participate in implicit mapping.
     * This is useful for preventing Bean types from
     * automatically mapping their properties to the target object, or to prevent properties from one source
     * overriding properties from other sources in multi-source scenarios.</li>
     * </ul>
     * <p>
     * <b>Map parameters:</b>
     * <ul>
     * <li>true: Enables Map entries to participate in implicit mapping.
     * This changes the default behavior
     * for Maps in multi-source scenarios,
     * allowing Map-to-Bean mapping to work similarly to single-source scenarios.</li>
     * <li>false : Disables automatic implicit mapping of Map entries. This maintains the same behavior
     * as not applying the annotation at all in multi-source scenarios,
     * where Map entries are not implicitly mapped by default.</li>
     * </ul>
     * <p>
     * Note: This setting only affects implicit mapping. Explicit mappings defined with
     * {@literal @}Mapping annotations are always processed regardless of this setting.
     *
     * @return whether implicit mapping should be enabled for this parameter
     */
    boolean implicitMapping() default true;

    /**
     * Marks this parameter as primary in multi-source mapping scenarios.
     * <p>
     * When multiple source parameters contain properties with the same name that could be mapped to
     * a target property, MapStruct normally reports an error due to the ambiguity. When a parameter
     * is marked as primary:
     * <p>
     * <ul>
     * <li>If conflict occurs between properties from different source parameters, the property from the
     * primary-marked parameter will be used</li>
     * <li>If multiple parameters are marked as primary and have conflicting properties, MapStruct will
     * still report an error</li>
     * </ul>
     * <p>
     * Note: This setting affects both implicit and explicit mappings when resolving conflicts.
     * Explicit {@literal @}Mapping annotations always take precedence over primary parameter selection.
     *
     * @return whether this parameter should be considered primary when resolving conflicts
     * @since 1.7.0
     */
    boolean primary() default false;
}
