/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotatewith;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention( RUNTIME )
@Target( { METHOD, TYPE } )
public @interface CustomAnnotationWithParams {
    String stringParam();

    Class<? extends Annotation> genericTypedClass() default CustomAnnotationWithParams.class;

    AnnotateWithEnum enumParam() default AnnotateWithEnum.EXISTING;

    byte byteParam() default 0x00;

    char charParam() default 'a';

    double doubleParam() default 0.0;

    float floatParam() default 0.0f;

    int intParam() default 0;

    long longParam() default 0L;

    short shortParam() default 0;

    boolean booleanParam() default false;

    short[] shortArray() default {};

    byte[] byteArray() default {};

    int[] intArray() default {};

    long[] longArray() default {};

    float[] floatArray() default {};

    double[] doubleArray() default {};

    char[] charArray() default {};

    boolean[] booleanArray() default {};

    String[] stringArray() default {};

    Class<?>[] classArray() default {};

    AnnotateWithEnum[] enumArray() default {};
}
