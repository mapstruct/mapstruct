/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.intellij.lang.annotations.Language;

/**
 * Specifies the kotlin sources to compile during an annotation processor test.
 * If given both on the class-level and the method-level for a given test, all the given sources will be compiled.
 *
 * @author Filip Hrisafov
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface WithKotlinSources {

    /**
     * The classes to be compiled for the annotated test class or method.
     *
     * @return the classes to be compiled for the annotated test class or method
     */
    @Language(value = "file-reference", prefix = "", suffix = ".kt")
    String[] value();
}
