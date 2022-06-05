package org.mapstruct.ap.test.annotatewith;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;

@Mapper
@AnnotateWith( value = CustomAnnotation.class, elements = @AnnotateWith.Element( strings = "value" ) )
public interface AnnotationWithoutElementNameMapper {

}
