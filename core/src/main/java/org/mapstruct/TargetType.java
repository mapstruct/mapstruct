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
 * Declares a parameter of a custom mapping method to be populated with the target type of the mapping.
 * <p>
 * Not more than one parameter can be declared as {@code TargetType} and that parameter needs to be of type
 * {@link Class} (may be parameterized), or a super-type of it.
 *
 * @author Andreas Gudian
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.CLASS)
public @interface TargetType {
}
