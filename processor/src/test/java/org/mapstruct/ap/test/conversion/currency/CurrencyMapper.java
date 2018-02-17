package org.mapstruct.ap.test.conversion.currency;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Darren Rambaud (2/16/18)
 * <p>
 * darren@rambaud.io
 */
@Mapper
public interface CurrencyMapper {
    CurrencyMapper INSTANCE = Mappers.getMapper( CurrencyMapper.class );

    CurrencyTarget currencySourceToCurrencyTarget(CurrencySource source);

    CurrencySource currencyTargetToCurrencySource(CurrencyTarget target);
}
