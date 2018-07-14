/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1180;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
@Mapper( config = SharedConfig.class )
public abstract class ErroneousIssue1180Mapper {

    @InheritConfiguration
    public abstract Target map(Source source);

}
