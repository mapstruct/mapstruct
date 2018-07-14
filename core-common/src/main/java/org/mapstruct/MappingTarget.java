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
 * Declares a parameter of a mapping method to be the target of the mapping.
 * <p>
 * Not more than one parameter can be declared as {@code MappingTarget}.
 * <p>
 * <b>NOTE:</b> The parameter passed as a mapping target <b>must</b> not be {@code null}.
 *
 * @author Andreas Gudian
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.CLASS)
public @interface MappingTarget {
}
