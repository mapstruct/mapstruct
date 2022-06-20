/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.testutil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies the group id of the additional test dependencies that should be added to the test compile path
 *
 * @author Filip Hrisafov
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Repeatable( WithTestDependencies.class )
public @interface WithTestDependency {
    /**
     * @return The group ids of the additional test dependencies for the test compile path
     */
    String[] value();

}
