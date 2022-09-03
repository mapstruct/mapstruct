/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotatewith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.AnnotateWith;

@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.METHOD } )
@AnnotateWith( CustomMethodOnlyAnnotation.class )
public @interface MethodMetaAnnotation {

}
