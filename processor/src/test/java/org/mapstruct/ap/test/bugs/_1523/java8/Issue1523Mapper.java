/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1523.java8;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Christian Bandowski
 */
@Mapper
public abstract class Issue1523Mapper {

    public static final Issue1523Mapper INSTANCE = Mappers.getMapper( Issue1523Mapper.class );

    public abstract Target map(Source source);

}
