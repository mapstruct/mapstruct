/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.testutil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies the classes to compile during an annotation processor test. If given both on the class-level and the
 * method-level for a given test, all the given classes will be compiled.
 *
 * @author Gunnar Morling
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface WithClasses {

    /**
     * The classes to be compiled for the annotated test class or method.
     *
     * @return the classes to be compiled for the annotated test class or method
     */
    Class<?>[] value();
}
