/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1340;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue1340Mapper {

    Issue1340Mapper INSTANCE = Mappers.getMapper( Issue1340Mapper.class );

    enum Quote {
        SINGLE,
        MULTI
    }

    enum QuoteDto {
        SINGLE,
        MULTI
    }

    QuoteDto map(Quote quote, @Context Integer locale);

    Quote map(@Context Integer locale, QuoteDto quoteDto);
}
