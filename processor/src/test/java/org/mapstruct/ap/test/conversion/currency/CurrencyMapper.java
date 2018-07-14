/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.currency;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Darren Rambaud
 */
@Mapper
public interface CurrencyMapper {
    CurrencyMapper INSTANCE = Mappers.getMapper( CurrencyMapper.class );

    CurrencyTarget currencySourceToCurrencyTarget(CurrencySource source);

    CurrencySource currencyTargetToCurrencySource(CurrencyTarget target);
}
