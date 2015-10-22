/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.defaultvalue;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

@IssueKey( "600" )
@RunWith( AnnotationProcessorTestRunner.class )
@WithClasses( {
        CountryEntity.class,
        CountryDts.class
} )
public class DefaultValueTest {
    @Test
    @WithClasses( {
            Region.class,
            CountryMapper.class
    } )
    /**
     * Checks:
     * <ul>
     *     <li>On code: Using defaultValue without type conversion</li>
     *     <li>On id: Type conversion of the defaultValue (string expr to int)</li>
     *     <li>On name: Using ConstantExpression instead of defaultValue</li>
     *     <li>On zipcode: Ignoring defaultValue on primitive target types</li>
     *     <li>On region: Using defaultValue before the assignment by an intern method (mapToString)</li>
     * </ul>
     */
    public void shouldDefaultValueAndUseConstantExpression() {
        CountryEntity countryEntity = new CountryEntity();

        CountryDts countryDts = CountryMapper.INSTANCE.mapToCountryDts( countryEntity );

        // id is null so it should fall back to the default value
        assertThat( countryDts.getId() ).isEqualTo( 42 );

        // code is null so it should fall back to the default value
        assertThat( countryDts.getCode() ).isEqualTo( "DE" );

        assertThat( countryDts.getZipcode() ).isEqualTo( 0 );

        assertThat( countryDts.getRegion() ).isEqualTo( "someRegion" );
    }

    @Test
    @WithClasses( {
            Region.class,
            CountryMapper.class
    } )
    public void shouldIgnoreDefaultValue() {
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setCode( "US" );
        Region region = new Region();
        region.setCode( "foobar" );
        countryEntity.setRegion( region );

        CountryDts countryDts = CountryMapper.INSTANCE.mapToCountryDts( countryEntity );

        // the source entity had a code set, so the default value shouldn't be used
        assertThat( countryDts.getCode() ).isEqualTo( "US" );
        assertThat( countryDts.getRegion() ).isEqualTo( "foobar" );
    }

    @Test
    @WithClasses( {
            Region.class,
            CountryMapper.class
    } )
    public void shouldHandleUpdateMethodsFromDtsToEntity() {
        CountryEntity countryEntity = new CountryEntity();
        CountryDts countryDts = new CountryDts();

        CountryMapper.INSTANCE.mapToCountryDts( countryDts, countryEntity );

        assertThat( countryEntity.getId() ).isEqualTo( 0 );
        // no code is set, so fall back to default value
        assertThat( countryEntity.getCode() ).isEqualTo( "DE" );
        assertThat( countryEntity.getZipcode() ).isEqualTo( 0 );
    }

    @Test
    @WithClasses( {
            Region.class,
            CountryMapper.class
    } )
    public void shouldHandleUpdateMethodsFromEntityToEntity() {
        CountryEntity source = new CountryEntity();
        CountryEntity target = new CountryEntity();

        CountryMapper.INSTANCE.mapToCountryDts( source, target );

        // no id is set, so fall back to default value
        assertThat( target.getId() ).isEqualTo( 42 );
        // no code is set, so fall back to default value
        assertThat( target.getCode() ).isEqualTo( "DE" );
        assertThat( target.getZipcode() ).isEqualTo( 0 );
    }

    @Test
    @WithClasses( {
            ErroneousMapper.class,
            Region.class,
    } )
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                    @Diagnostic( type = ErroneousMapper.class,
                            kind = javax.tools.Diagnostic.Kind.ERROR,
                            line = 33,
                            messageRegExp = "Constant and default value are both defined in @Mapping,"
                                    + " either define a defaultValue or a constant." ),
                    @Diagnostic(type = ErroneousMapper.class,
                            kind = javax.tools.Diagnostic.Kind.ERROR,
                            line = 33,
                            messageRegExp = "Can't map property \".*Region region\" to \".*String region\"\\. Consider")
            }
    )
    public void errorOnDefaultValueAndConstant() throws ParseException {
    }

    @Test
    @WithClasses( {
            ErroneousMapper2.class,
            Region.class,
    } )
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                    @Diagnostic( type = ErroneousMapper2.class,
                            kind = javax.tools.Diagnostic.Kind.ERROR,
                            line = 33,
                            messageRegExp = "Expression and default value are both defined in @Mapping,"
                                    + " either define a defaultValue or an expression." ),
                    @Diagnostic(type = ErroneousMapper2.class,
                            kind = javax.tools.Diagnostic.Kind.ERROR,
                            line = 33,
                            messageRegExp = "Can't map property \".*Region region\" to \".*String region\"\\. Consider")
            }
    )
    public void errorOnDefaultValueAndExpression() throws ParseException {
    }

}
