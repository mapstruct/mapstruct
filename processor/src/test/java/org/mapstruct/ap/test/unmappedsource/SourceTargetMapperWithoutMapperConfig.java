/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.unmappedsource;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.unmappedtarget.Source;
import org.mapstruct.ap.test.unmappedtarget.Target;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Yusuf Kemal Ozcan
 */
@Mapper
public interface SourceTargetMapperWithoutMapperConfig {

    SourceTargetMapperWithoutMapperConfig INSTANCE = Mappers.getMapper( SourceTargetMapperWithoutMapperConfig.class );

    Target sourceToTarget(Source source);

    Source targetToSource(Target target);
}
