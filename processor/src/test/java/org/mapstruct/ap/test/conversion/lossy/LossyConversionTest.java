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
package org.mapstruct.ap.test.conversion.lossy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

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
    VerySpecialNumberMapper.class})
@IssueKey("5")
public class LossyConversionTest {

    @Test
    @WithClasses(ErroneousKitchenDrawerMapper1.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousKitchenDrawerMapper1.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 37,
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
                line = 37,
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
                line = 37,
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
                line = 37,
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
                line = 37,
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
                line = 37,
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
                line = 34,
                messageRegExp = "collection element has a possibly lossy conversion from java.math.BigInteger to "
                    + "java.math.BigDecimal")
        })
    public void testListElementConversion() {
    }

    @Test
    @WithClasses(MapMapper.class)
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = MapMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 32,
                messageRegExp = "map key has a possibly lossy conversion from java.lang.Long to java.lang.Integer."),
            @Diagnostic(type = MapMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 32,
                messageRegExp = "map value has a possibly lossy conversion from java.lang.Double to java.lang.Float.")
    })
    public void testMapElementConversion() {
    }
}
