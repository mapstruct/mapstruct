/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.composition;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

/**
 * @author orange add
 */
@Retention( RetentionPolicy.CLASS )
@ValueMapping(source = "EXTRA", target = "SPECIAL")
@ValueMapping(source = MappingConstants.ANY_REMAINING, target = "DEFAULT")
public @interface CustomValueAnnotation {
}
