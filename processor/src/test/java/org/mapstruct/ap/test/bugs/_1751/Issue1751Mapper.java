/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1751;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue1751Mapper {

    Issue1751Mapper INSTANCE = Mappers.getMapper( Issue1751Mapper.class );

    Target map(Source source);

    default Holder<Target> mapToHolder(Source source) {
        return new Holder<>( this.map( source ) );
    }
}
