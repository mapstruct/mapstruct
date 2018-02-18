/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
