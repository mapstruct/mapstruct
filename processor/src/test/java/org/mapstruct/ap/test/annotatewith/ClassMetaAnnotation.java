package org.mapstruct.ap.test.annotatewith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.AnnotateWith;

@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.TYPE } )
@AnnotateWith( CustomClassOnlyAnnotation.class )
public @interface ClassMetaAnnotation {

}
