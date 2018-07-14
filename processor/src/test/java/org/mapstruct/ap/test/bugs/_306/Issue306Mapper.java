/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._306;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.TargetType;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class Issue306Mapper {

    public static final Issue306Mapper INSTANCE = Mappers.getMapper( Issue306Mapper.class );

    public abstract Source targetToSource(Target target);

    protected void dummy1( Set<String> arg ) {
        throw new RuntimeException();
    }

    protected Set<Long> dummy2( Object object,  @TargetType Class<?> clazz ) {
        throw new RuntimeException();
    }
}
