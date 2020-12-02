/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotations;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-02-06T16:20:45+0100",
    comments = "version: , compiler: javac, environment: Java 11.0.9.1 (AdoptOpenJDK)"
)
@MyGeneratedAnnotation
public class AnnotatedMapperImpl implements AnnotatedMapper {

    private final AnnotatedMapper instanceMapper = AnnotatedMapper.INSTANCE;

    @Override
    public Target map(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target(source.getId());

        return target;
    }
}
