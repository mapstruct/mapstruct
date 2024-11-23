/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.accessibility.referenced;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class AbstractSourceTargetMapperProtected extends SourceTargetmapperProtectedBase {

    public static final AbstractSourceTargetMapperProtected INSTANCE =
            Mappers.getMapper( AbstractSourceTargetMapperProtected.class );

    @Mapping(target = "referencedTarget", source = "referencedSource")
    public abstract Target toTarget(Source source);
}
