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
 * FIXME
 *
 * @author Oliver Erhart
 * @since 1.7
 * @see Condition @Condition
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.CLASS)
@Condition(appliesTo = ConditionStrategy.ITERABLE_ELEMENTS)
public @interface IterableElementCondition {

}
