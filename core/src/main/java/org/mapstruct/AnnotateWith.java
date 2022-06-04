/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * This can be used to have mapstruct generate additional annotations on classes/methods.
 *
 * @author Ben Zegveld
 * @since 1.6
 */
@Repeatable( AnnotateWiths.class )
@Retention( SOURCE )
@Target( { TYPE, METHOD, ANNOTATION_TYPE } )
public @interface AnnotateWith {
    /**
     * @return the annotation class that needs to be added.
     */
    Class<? extends Annotation> value();

    /**
     * @return the annotation elements that are to be applied to this annotation.
     */
    Element[] elements() default {};

    /**
     * Used in combination with {@link AnnotateWith} to configure the annotation elements. Only 1 value type may be used
     * within the same annotation at a time. For example mixing shorts and ints is not allowed.
     *
     * @author Ben Zegveld
     * @since 1.6
     */
    @interface Element {
        /**
         * @return name of the annotation element.
         */
        String name();

        /**
         * cannot be used in conjunction with other value fields within the same annotation element.
         *
         * @return short value(s) for the annotation element.
         */
        short[] shorts() default {};

        /**
         * cannot be used in conjunction with other value fields within the same annotation element.
         *
         * @return byte value(s) for the annotation element.
         */
        byte[] bytes() default {};

        /**
         * cannot be used in conjunction with other value fields within the same annotation element.
         *
         * @return int value(s) for the annotation element.
         */
        int[] ints() default {};

        /**
         * cannot be used in conjunction with other value fields within the same annotation element.
         *
         * @return long value(s) for the annotation element.
         */
        long[] longs() default {};

        /**
         * cannot be used in conjunction with other value fields within the same annotation element.
         *
         * @return float value(s) for the annotation element.
         */
        float[] floats() default {};

        /**
         * cannot be used in conjunction with other value fields within the same annotation element.
         *
         * @return double value(s) for the annotation element.
         */
        double[] doubles() default {};

        /**
         * cannot be used in conjunction with other value fields within the same annotation element.
         *
         * @return char value(s) for the annotation element.
         */
        char[] chars() default {};

        /**
         * cannot be used in conjunction with other value fields within the same annotation element.
         *
         * @return boolean value(s) for the annotation element.
         */
        boolean[] booleans() default {};

        /**
         * cannot be used in conjunction with other value fields within the same annotation element.
         *
         * @return string value(s) for the annotation element.
         */
        String[] strings() default {};

        /**
         * cannot be used in conjunction with other value fields within the same annotation element.
         *
         * @return class value(s) for the annotation element.
         */
        Class<?>[] classes() default {};

        /**
         * cannot be used in conjunction with other value fields within the same annotation element.
         *
         * @return enum value(s) for the annotation element.
         */
        EnumElement[] enums() default {};

    }

    /**
     * Used in combination with {@link AnnotateWith.Element} to configure the annotation element enum values.
     *
     * @author Ben Zegveld
     * @since 1.6
     */
    @interface EnumElement {
        /**
         * @return the class of the enum.
         */
        Class<? extends Enum<?>> enumClass();

        /**
         * @return the name of the enum value.
         */
        String name();
    }
}
