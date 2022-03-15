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
 * @since 1.5
 */
@Repeatable( AnnotateWiths.class )
@Retention( SOURCE )
@Target( { TYPE, METHOD, ANNOTATION_TYPE } )
public @interface AnnotateWith {
    Class<? extends Annotation> value();

    Parameter[] parameters() default {};

    @interface Parameter {
        String key();

        short[] shorts() default {};

        byte[] bytes() default {};

        int[] ints() default {};

        long[] longs() default {};

        float[] floats() default {};

        double[] doubles() default {};

        char[] chars() default {};

        boolean[] booleans() default {};

        String[] strings() default {};

        Class<?>[] classes() default {};
    }
}
