/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.currency;

import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Darren Rambaud
 */
@WithClasses({ CurrencyMapper.class, CurrencySource.class, CurrencyTarget.class })
@IssueKey("1355")
@RunWith(AnnotationProcessorTestRunner.class)
public class CurrencyConversionTest {

    @Test
    public void shouldApplyCurrencyConversions() {
        final CurrencySource source = new CurrencySource();
        source.setCurrencyA( Currency.getInstance( "USD" ) );
        Set<Currency> currencies = new HashSet<Currency>();
        currencies.add( Currency.getInstance( "EUR" ) );
        currencies.add( Currency.getInstance( "CHF" ) );
        source.setUniqueCurrencies( currencies );

        CurrencyTarget target = CurrencyMapper.INSTANCE.currencySourceToCurrencyTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getCurrencyA() ).isEqualTo( "USD" );
        assertThat( target.getUniqueCurrencies() )
            .isNotEmpty()
            .containsExactlyInAnyOrder( "EUR", "CHF" );
    }

    @Test
    public void shouldApplyReverseConversions() {
        final CurrencyTarget target = new CurrencyTarget();
        target.setCurrencyA( "USD" );
        target.setUniqueCurrencies( Collections.asSet( "JPY" ) );

        CurrencySource source = CurrencyMapper.INSTANCE.currencyTargetToCurrencySource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getCurrencyA().getCurrencyCode() ).isEqualTo( Currency.getInstance( "USD" )
            .getCurrencyCode() );
        assertThat( source.getUniqueCurrencies() ).containsExactlyInAnyOrder( Currency.getInstance( "JPY" ) );
    }
}
