/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.accessibility.referenced;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public abstract class AbstractSourceTargetMapperPrivate extends SourceTargetMapperPrivateBase {

    public static final AbstractSourceTargetMapperPrivate INSTANCE =
            Mappers.getMapper( AbstractSourceTargetMapperPrivate.class );

    @Mapping(target = "referencedTarget", source = "referencedSource")
    public abstract Target toTarget(Source source);
}
