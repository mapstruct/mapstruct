/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mapstruct.itest.externalbeanjar;

import org.mapstruct.itest.externalbeanjar.Source;
import org.mapstruct.itest.externalbeanjar.Target;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface Issue1121Mapper {

    Issue1121Mapper INSTANCE = Mappers.getMapper( Issue1121Mapper.class );

    @Mapping(target = "integer", source = "bigDecimal")
    Target map(Source source);

}
