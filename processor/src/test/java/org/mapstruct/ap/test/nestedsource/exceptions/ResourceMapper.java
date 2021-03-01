/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedsource.exceptions;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author Richard Lea <chigix@zoho.com>
 */
@Mapper
public interface ResourceMapper {

    @Mapping(target = "userId", source = "bucket.user.uuid")
    ResourceDto map(Resource r) throws NoSuchUser;

}
