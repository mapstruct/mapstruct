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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Darren Rambaud (2/16/18)
 */
@IssueKey("1355")
@RunWith(AnnotationProcessorTestRunner.class)
public class CurrencyConversionTest {

    @Test
    @IssueKey("1355")
    @WithClasses({ CurrencyMapper.class, CurrencySource.class, CurrencyTarget.class })
    public void shouldApplyCurrencyConversions() {
        final CurrencySource source = new CurrencySource();
        source.setCurrencyA( Currency.getInstance( "USD" ) );
        source.setCurrencyB( Currency.getInstance( "GBP" ) );
        source.setCurrencyC( Currency.getInstance( "EUR" ) );
        source.setCurrencyD( Currency.getInstance( "PHP" ) );
        source.setUniqueCurrencies( Currency.getAvailableCurrencies() );

        final CurrencyTarget target = CurrencyMapper.INSTANCE.currencySourceToCurrencyTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getCurrencyA() ).isEqualTo( "USD" );
        assertThat( target.getCurrencyB() ).isEqualTo( "GBP" );
        assertThat( target.getCurrencyC() ).isEqualTo( "EUR" );
        assertThat( target.getCurrencyD() ).isEqualTo( "PHP" );
        assertThat( target.getUniqueCurrencies().isEmpty() ).isFalse();
        assertThat( target.getUniqueCurrencies().size() ).isEqualTo( Currency.getAvailableCurrencies().size() );
    }

    @Test
    @IssueKey("1355")
    @WithClasses({ CurrencyMapper.class, CurrencySource.class, CurrencyTarget.class })
    public void shouldApplyReverseConversions() {
        final CurrencyTarget target = new CurrencyTarget();
        target.setCurrencyA( "USD" );
        target.setCurrencyB( "GBP" );
        target.setCurrencyC( "EUR" );
        target.setCurrencyD( "PHP" );
        target.setUniqueCurrencies( Collections.asSet( "JPY" ) );

        final CurrencySource source = CurrencyMapper.INSTANCE.currencyTargetToCurrencySource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getCurrencyA().getCurrencyCode() ).isEqualTo( Currency.getInstance( "USD" )
            .getCurrencyCode() );
        assertThat( source.getCurrencyB().getCurrencyCode() ).isEqualTo( Currency.getInstance( "GBP" )
            .getCurrencyCode() );
        assertThat( source.getCurrencyC().getCurrencyCode() ).isEqualTo( Currency.getInstance( "EUR" )
            .getCurrencyCode() );
        assertThat( source.getCurrencyD().getCurrencyCode() ).isEqualTo( Currency.getInstance( "PHP" )
            .getCurrencyCode() );
        assertThat( source.getUniqueCurrencies().isEmpty() ).isFalse();
    }
}
