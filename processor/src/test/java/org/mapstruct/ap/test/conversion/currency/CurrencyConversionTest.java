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
 * <p>
 * darren@rambaud.io
 */
@IssueKey("1355")
@RunWith(AnnotationProcessorTestRunner.class)
public class CurrencyConversionTest {

    @Test
    @IssueKey("1355")
    @WithClasses({ CurrencyMapper.class, CurrencySource.class, CurrencyTarget.class })
    public void shouldApplyCurrencyConversions() {
        CurrencySource source = new CurrencySource();
        source.setA( Currency.getInstance( "USD" ) );
        source.setB( Currency.getInstance( "GBP" ) );
        source.setC( Currency.getInstance( "EUR" ) );
        source.setD( Currency.getInstance( "PHP" ) );

        CurrencyTarget target = CurrencyMapper.INSTANCE.currencySourceToCurrencyTarget( source );

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
        CurrencyTarget target = new CurrencyTarget();
        target.setA( "USD" );
        target.setB( "GBP" );
        target.setC( "EUR" );
        target.setD( "PHP" );

        CurrencySource source = CurrencyMapper.INSTANCE.currencyTargetToCurrencySource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getA().getCurrencyCode() ).isEqualTo( Currency.getInstance( "USD" ).getCurrencyCode() );
        assertThat( source.getB().getCurrencyCode() ).isEqualTo( Currency.getInstance( "GBP" ).getCurrencyCode() );
        assertThat( source.getC().getCurrencyCode() ).isEqualTo( Currency.getInstance( "EUR" ).getCurrencyCode() );
        assertThat( source.getD().getCurrencyCode() ).isEqualTo( Currency.getInstance( "PHP" ).getCurrencyCode() );
    }
}
