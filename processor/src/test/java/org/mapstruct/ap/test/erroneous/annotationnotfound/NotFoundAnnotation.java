/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.annotationnotfound;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Sjaak Derksen
 */
@Retention(RetentionPolicy.CLASS)
@java.lang.annotation.Target(ElementType.METHOD)
public @interface NotFoundAnnotation {

}
