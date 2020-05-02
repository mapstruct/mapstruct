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
 * @author Filip Hrisafov
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface EnumMapping {

    String nameTransformStrategy();

    String configuration();
}
