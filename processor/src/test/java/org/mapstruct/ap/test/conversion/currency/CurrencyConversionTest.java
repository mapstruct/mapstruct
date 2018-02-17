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
        source.setA( Currency.getInstance( "USD" ) );
        source.setB( Currency.getInstance( "GBP" ) );
        source.setC( Currency.getInstance( "EUR" ) );
        source.setD( Currency.getInstance( "PHP" ) );

        final CurrencyTarget target = CurrencyMapper.INSTANCE.currencySourceToCurrencyTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getA() ).isEqualTo( "USD" );
        assertThat( target.getB() ).isEqualTo( "GBP" );
        assertThat( target.getC() ).isEqualTo( "EUR" );
        assertThat( target.getD() ).isEqualTo( "PHP" );
    }

    @Test
    @IssueKey("1355")
    @WithClasses({ CurrencyMapper.class, CurrencySource.class, CurrencyTarget.class })
    public void shouldApplyReverseConversions() {
        final CurrencyTarget target = new CurrencyTarget();
        target.setA( "USD" );
        target.setB( "GBP" );
        target.setC( "EUR" );
        target.setD( "PHP" );

        final CurrencySource source = CurrencyMapper.INSTANCE.currencyTargetToCurrencySource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getA().getCurrencyCode() ).isEqualTo( Currency.getInstance( "USD" ).getCurrencyCode() );
        assertThat( source.getB().getCurrencyCode() ).isEqualTo( Currency.getInstance( "GBP" ).getCurrencyCode() );
        assertThat( source.getC().getCurrencyCode() ).isEqualTo( Currency.getInstance( "EUR" ).getCurrencyCode() );
        assertThat( source.getD().getCurrencyCode() ).isEqualTo( Currency.getInstance( "PHP" ).getCurrencyCode() );
    }
}
