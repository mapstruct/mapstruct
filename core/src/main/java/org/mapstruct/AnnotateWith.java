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

import org.mapstruct.HiddenEnumForAnnotationDefault.Dummy;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * This can be used to have mapstruct generate additional annotations on classes/methods.
 *
 * @author Ben Zegveld
 * @since 1.6
 */
@Repeatable( AnnotateWiths.class )
@Retention( CLASS )
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
         * cannot be used in conjunction with other value fields.
         *
         * @return short value(s) for the annotation element.
         */
        short[] shorts() default {};

        /**
         * cannot be used in conjunction with other value fields.
         *
         * @return byte value(s) for the annotation element.
         */
        byte[] bytes() default {};

        /**
         * cannot be used in conjunction with other value fields.
         *
         * @return int value(s) for the annotation element.
         */
        int[] ints() default {};

        /**
         * cannot be used in conjunction with other value fields.
         *
         * @return long value(s) for the annotation element.
         */
        long[] longs() default {};

        /**
         * cannot be used in conjunction with other value fields.
         *
         * @return float value(s) for the annotation element.
         */
        float[] floats() default {};

        /**
         * cannot be used in conjunction with other value fields.
         *
         * @return double value(s) for the annotation element.
         */
        double[] doubles() default {};

        /**
         * cannot be used in conjunction with other value fields.
         *
         * @return char value(s) for the annotation element.
         */
        char[] chars() default {};

        /**
         * cannot be used in conjunction with other value fields.
         *
         * @return boolean value(s) for the annotation element.
         */
        boolean[] booleans() default {};

        /**
         * cannot be used in conjunction with other value fields.
         *
         * @return string value(s) for the annotation element.
         */
        String[] strings() default {};

        /**
         * cannot be used in conjunction with other value fields.
         *
         * @return class value(s) for the annotation element.
         */
        Class<?>[] classes() default {};

        /**
         * only used in conjunction with the {@link #enums()} annotation element.
         *
         * @return the class of the enum.
         */
        Class<? extends Enum<?>> enumClass() default Dummy.class;

        /**
         * cannot be used in conjunction with other value fields. {@link #enumClass()} is also required when using
         * {@link #enums()}
         *
         * @return enum value(s) for the annotation element.
         */
        String[] enums() default {};

    }
}

class HiddenEnumForAnnotationDefault {
    enum Dummy {
    }
}
