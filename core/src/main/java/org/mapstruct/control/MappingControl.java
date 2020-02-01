/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Repeatable(MappingControls.class)
@Target( ElementType.ANNOTATION_TYPE )
@MappingControl( MappingControl.Use.DIRECT )
@MappingControl( MappingControl.Use.BUILT_IN_CONVERSION )
@MappingControl( MappingControl.Use.MAPPING_METHOD )
@MappingControl( MappingControl.Use.COMPLEX_MAPPING )
public @interface MappingControl {

    Use value();

    enum Use {

        /**
         * Controls the mapping, allows for type conversion from source type to target type
         * <p>
         * Type conversions are typically suppored directly in Java. The "toString()" is such an example,
         * which allows for mapping for instance a  {@link java.lang.Number} type to a {@link java.lang.String}.
         * <p>
         * Please refer to the MapStruct guide for more info.
         *
         * @since 1.4
         */
        BUILT_IN_CONVERSION,

        /**
         * Controls the mapping from source to target type, allows mapping by calling:
         * <ol>
         * <li>a type conversion, passed into a mapping method</li>
         * <li>a mapping method, passed into a type conversion</li>
         * <li>a mapping method passed into another mapping method</li>
         * </ol>
         *
         * @since 1.4
         */
        COMPLEX_MAPPING,
        /**
         * Controls the mapping, allows for a direct mapping from source type to target type.
         * <p>
         * This means if source type and target type are of the same type, MapStruct will not perform
         * any mappings anymore and assign the target to the source direct.
         * <p>
         * An exception are types from the package {@link java}, which will be mapped always directly.
         *
         * @since 1.4
         */
        DIRECT,

        /**
         * Controls the mapping, allows for Direct Mapping from source type to target type.
         * <p>
         * The mapping method can be either a custom refered mapping method, or a MapStruct built in
         * mapping method.
         *
         * @since 1.4
         */
        MAPPING_METHOD
    }

}
