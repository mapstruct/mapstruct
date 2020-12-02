package org.mapstruct.ap.test.annotations;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Raimund Klein
 */
@Mapper(implementationAnnotations = MyGeneratedAnnotation.class)
public interface AnnotatedMapper {
    AnnotatedMapper INSTANCE = Mappers.getMapper(AnnotatedMapper.class);

    Target map(Source source);
}
