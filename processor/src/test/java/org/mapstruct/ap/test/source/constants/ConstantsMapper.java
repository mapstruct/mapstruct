/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.constants;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface ConstantsMapper {

    ConstantsMapper INSTANCE = Mappers.getMapper( ConstantsMapper.class );

    @Mapping(target = "booleanValue", constant = "true")
    @Mapping(target = "booleanBoxed", constant = "false")
    @Mapping(target = "charValue", constant = "'b'")
    @Mapping(target = "charBoxed", constant = "'a'")
    @Mapping(target = "byteValue", constant = "20")
    @Mapping(target = "byteBoxed", constant = "-128")
    @Mapping(target = "shortValue", constant = "1996")
    @Mapping(target = "shortBoxed", constant = "-1996")
    @Mapping(target = "intValue", constant = "-03777777")
    @Mapping(target = "intBoxed", constant = "15")
    @Mapping(target = "longValue", constant = "0x7fffffffffffffffL")
    @Mapping(target = "longBoxed", constant = "0xCAFEBABEL")
    @Mapping(target = "floatValue", constant = "1.40e-45f")
    @Mapping(target = "floatBoxed", constant = "3.4028235e38f")
    @Mapping(target = "doubleValue", constant = "1e137")
    @Mapping(target = "doubleBoxed", constant = "0x0.001P-1062d")
    @Mapping(target = "doubleBoxedZero", constant = "0.0")
    ConstantsTarget mapFromConstants( String dummy );
}
