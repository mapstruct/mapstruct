/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.lossy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

/**
 * Tests the conversion between Joda-Time types and String/Date/Calendar.
 *
 * @author Sjaak Derksen
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    OversizedKitchenDrawerDto.class,
    RegularKitchenDrawerEntity.class,
    VerySpecialNumber.class,
    VerySpecialNumberMapper.class,
    CutleryInventoryMapper.class,
    CutleryInventoryDto.class,
    CutleryInventoryEntity.class
})
@IssueKey("5")
public class LossyConversionTest {

    @Test
    public void testNoErrorCase() {

        CutleryInventoryDto dto = new CutleryInventoryDto();
        dto.setNumberOfForks( 5 );
        dto.setNumberOfKnifes( (short) 7 );
        dto.setNumberOfSpoons( (byte) 3 );
        dto.setApproximateKnifeLength( 3.7f );

        CutleryInventoryEntity entity = CutleryInventoryMapper.INSTANCE.map( dto );
        assertThat( entity.getNumberOfForks() ).isEqualTo( 5L );
        assertThat( entity.getNumberOfKnifes() ).isEqualTo( 7 );
        assertThat( entity.getNumberOfSpoons() ).isEqualTo( (short) 3 );
        assertThat( entity.getApproximateKnifeLength() ).isCloseTo( 3.7d, withinPercentage( 0.0001d ) );
    }

    @Test
    @WithClasses(ErroneousKitchenDrawerMapper1.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousKitchenDrawerMapper1.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 24,
                messageRegExp = "Can't map property \"long numberOfForks\". It has a possibly lossy conversion from "
                    + "long to int.")
        })
    public void testConversionFromlongToint() {
    }

    @Test
    @WithClasses(KitchenDrawerMapper2.class)
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = KitchenDrawerMapper2.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 24,
                messageRegExp = "property \"java.math.BigInteger numberOfKnifes\" has a possibly lossy conversion "
                    + "from java.math.BigInteger to java.lang.Integer.")
        })
    public void testConversionFromBigIntegerToInteger() {
    }

    @Test
    @WithClasses(ErroneousKitchenDrawerMapper3.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousKitchenDrawerMapper3.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 24,
                messageRegExp = "org.mapstruct.ap.test.conversion.lossy.VerySpecialNumber numberOfSpoons\". It has "
                    + "a possibly lossy conversion from java.math.BigInteger to java.lang.Long")
        })
    public void test2StepConversionFromBigIntegerToLong() {
    }

    @Test
    @WithClasses(ErroneousKitchenDrawerMapper4.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousKitchenDrawerMapper4.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 24,
                messageRegExp = "Can't map property \"java.lang.Double depth\". It has a possibly lossy conversion "
                    + "from java.lang.Double to float.")
        })
    public void testConversionFromDoubleTofloat() {
    }

    @Test
    @WithClasses(ErroneousKitchenDrawerMapper5.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousKitchenDrawerMapper5.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 24,
                messageRegExp = "\"java.math.BigDecimal length\". It has a possibly lossy conversion from "
                    + "java.math.BigDecimal to java.lang.Float.")
        })
    public void testConversionFromBigDecimalToFloat() {
    }

    @Test
    @WithClasses(KitchenDrawerMapper6.class)
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = KitchenDrawerMapper6.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 24,
                messageRegExp = "property \"double height\" has a possibly lossy conversion from double to float.")
        })
    public void test2StepConversionFromdoubleTofloat() {
    }

    @Test
    @WithClasses(ListMapper.class)
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = ListMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 21,
                messageRegExp = "collection element has a possibly lossy conversion from java.math.BigDecimal to "
                    + "java.math.BigInteger")
        })
    public void testListElementConversion() {
    }

    @Test
    @WithClasses(MapMapper.class)
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = MapMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 19,
                messageRegExp = "map key has a possibly lossy conversion from java.lang.Long to java.lang.Integer."),
            @Diagnostic(type = MapMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 19,
                messageRegExp = "map value has a possibly lossy conversion from java.lang.Double to java.lang.Float.")
    })
    public void testMapElementConversion() {
    }
}
