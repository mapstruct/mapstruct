/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.defaultvalue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.defaultvalue.other.Continent;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("600")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    CountryEntity.class,
    CountryDts.class,
    Continent.class
})
public class DefaultValueTest {
    @Test
    @WithClasses({
        Region.class,
        CountryMapper.class
    })
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
        assertThat( countryDts.getContinent() ).isEqualTo( Continent.EUROPE );
    }

    @Test
    @WithClasses({
        Region.class,
        CountryMapper.class
    })
    public void shouldIgnoreDefaultValue() {
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setCode( "US" );
        Region region = new Region();
        region.setCode( "foobar" );
        countryEntity.setRegion( region );
        countryEntity.setContinent( Continent.NORTH_AMERICA );

        CountryDts countryDts = CountryMapper.INSTANCE.mapToCountryDts( countryEntity );

        // the source entity had a code set, so the default value shouldn't be used
        assertThat( countryDts.getCode() ).isEqualTo( "US" );
        assertThat( countryDts.getRegion() ).isEqualTo( "foobar" );
        assertThat( countryDts.getContinent() ).isEqualTo( Continent.NORTH_AMERICA );
    }

    @Test
    @WithClasses({
        Region.class,
        CountryMapper.class
    })
    public void shouldHandleUpdateMethodsFromDtsToEntity() {
        CountryEntity countryEntity = new CountryEntity();
        CountryDts countryDts = new CountryDts();

        CountryMapper.INSTANCE.mapToCountryEntity( countryDts, countryEntity );

        assertThat( countryEntity.getId() ).isEqualTo( 0 );
        // no code is set, so fall back to default value
        assertThat( countryEntity.getCode() ).isEqualTo( "DE" );
        assertThat( countryEntity.getZipcode() ).isEqualTo( 0 );
        assertThat( countryEntity.getContinent() ).isEqualTo( Continent.EUROPE );
    }

    @Test
    @WithClasses({
        Region.class,
        CountryMapper.class
    })
    public void shouldHandleUpdateMethodsFromEntityToEntity() {
        CountryEntity source = new CountryEntity();
        CountryEntity target = new CountryEntity();

        CountryMapper.INSTANCE.mapToCountryEntity( source, target );

        // no id is set, so fall back to default value
        assertThat( target.getId() ).isEqualTo( 42 );
        // no code is set, so fall back to default value
        assertThat( target.getCode() ).isEqualTo( "DE" );
        assertThat( target.getZipcode() ).isEqualTo( 0 );
        assertThat( target.getContinent() ).isEqualTo( Continent.EUROPE );
    }

    @Test
    @WithClasses({
        ErroneousMapper.class,
        Region.class,
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 18,
                message = "Constant and default value are both defined in @Mapping, either define a defaultValue or a" +
                    " constant."),
            @Diagnostic(type = ErroneousMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 20,
                message = "Can't map property \"Region region\" to \"String region\". " +
                    "Consider to declare/implement a mapping method: \"String map(Region value)\".")
        }
    )
    public void errorOnDefaultValueAndConstant() {
    }

    @Test
    @WithClasses({
        ErroneousMapper2.class,
        Region.class,
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper2.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 18,
                message = "Expression and default value are both defined in @Mapping, either define a defaultValue or" +
                    " an expression."),
            @Diagnostic(type = ErroneousMapper2.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 20,
                message = "Can't map property \"Region region\" to \"String region\". " +
                    "Consider to declare/implement a mapping method: \"String map(Region value)\".")
        }
    )
    public void errorOnDefaultValueAndExpression() {
    }

    @Test
    @IssueKey("2214")
    @WithClasses({
        CountryMapperMultipleSources.class,
        Region.class,
    })
    public void shouldBeAbleToDetermineDefaultValueBasedOnOnlyTargetType() {
        CountryEntity entity = new CountryEntity();
        CountryDts target = CountryMapperMultipleSources.INSTANCE.map( entity, "ZH" );

        assertThat( target ).isNotNull();
        assertThat( target.getCode() ).isEqualTo( "CH" );
    }

    @Test
    @IssueKey("2220")
    @WithClasses({
        ErroneousMissingSourceMapper.class,
        Region.class,
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                type = ErroneousMissingSourceMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 17,
                message = "The type of parameter \"tenant\" has no property named \"type\"." +
                    " Please define the source property explicitly."),
        }
    )
    public void errorWhenOnlyTargetDefinedAndSourceDoesNotHaveProperty() {
    }

}
