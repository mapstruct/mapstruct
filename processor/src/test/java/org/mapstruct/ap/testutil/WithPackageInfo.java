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

/**
 * Specifies packages whose {@code package-info.java} should be included in the test compilation.
 * The value is a class from the package whose {@code package-info.java} should be compiled.
 *
 * @author Filip Hrisafov
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface WithPackageInfo {

    /**
     * A class from each package whose {@code package-info.java} should be compiled.
     *
     * @return classes identifying the packages
     */
    Class<?>[] value();
}
