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
package org.mapstruct.ap.test.source.constants;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.tools.Diagnostic.Kind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Sjaak Derksen
 */

@RunWith(AnnotationProcessorTestRunner.class)
public class SourceConstantsTest {

    @Test
    @IssueKey("187, 305")
    @WithClasses({
        Source.class,
        Source2.class,
        Target.class,
        CountryEnum.class,
        SourceTargetMapper.class,
        StringListMapper.class
    })
    public void shouldMapSameSourcePropertyToSeveralTargetProperties() throws ParseException {
        Source source = new Source();
        source.setPropertyThatShouldBeMapped( "SomeProperty" );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getPropertyThatShouldBeMapped() ).isEqualTo( "SomeProperty" );
        assertThat( target.getStringConstant() ).isEqualTo( "stringConstant" );
        assertThat( target.getEmptyStringConstant() ).isEqualTo( "" );
        assertThat( target.getIntegerConstant() ).isEqualTo( 14 );
        assertThat( target.getLongWrapperConstant() ).isEqualTo( new Long( 3001L ) );
        assertThat( target.getDateConstant() ).isEqualTo( getDate( "dd-MM-yyyy", "09-01-2014" ) );
        assertThat( target.getNameConstants() ).isEqualTo( Arrays.asList( "jack", "jill", "tom" ) );
        assertThat( target.getCountry() ).isEqualTo( CountryEnum.THE_NETHERLANDS );
    }

    @Test
    @IssueKey("187")
    @WithClasses({
        Source.class,
        Target.class,
        CountryEnum.class,
        SourceTargetMapper.class,
        StringListMapper.class
    })
    public void shouldMapTargetToSourceWithoutWhining() throws ParseException {
        Target target = new Target();
        target.setPropertyThatShouldBeMapped( "SomeProperty" );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( target.getPropertyThatShouldBeMapped() ).isEqualTo( "SomeProperty" );
    }

    @Test
    @IssueKey("187")
    @WithClasses({
        Source.class,
        Target.class,
        CountryEnum.class,
        ErroneousMapper1.class,
        StringListMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper1.class,
                kind = Kind.ERROR,
                line = 37,
                messageRegExp = "Source and constant are both defined in @Mapping, either define a source or a "
                    + "constant"),
            @Diagnostic(type = ErroneousMapper1.class,
                kind = Kind.WARNING,
                line = 43,
                messageRegExp = "Unmapped target property: \"integerConstant\"")
        }
    )
    public void errorOnSourceAndConstant() throws ParseException {
    }

    @Test
    @IssueKey("187")
    @WithClasses({
        Source.class,
        Target.class,
        CountryEnum.class,
        ErroneousMapper3.class,
        StringListMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper3.class,
                kind = Kind.ERROR,
                line = 37,
                messageRegExp =
                    "Expression and constant are both defined in @Mapping, either define an expression or a "
                        + "constant"),
            @Diagnostic(type = ErroneousMapper3.class,
                kind = Kind.WARNING,
                line = 43,
                messageRegExp = "Unmapped target property: \"integerConstant\"")
        }
    )
    public void errorOnConstantAndExpression() throws ParseException {
    }

    @Test
    @IssueKey("187")
    @WithClasses({
        Source.class,
        Target.class,
        CountryEnum.class,
        ErroneousMapper4.class,
        StringListMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper4.class,
                kind = Kind.ERROR,
                line = 37,
                messageRegExp = "Source and expression are both defined in @Mapping, either define a source or an "
                    + "expression"),
            @Diagnostic(type = ErroneousMapper4.class,
                kind = Kind.WARNING,
                line = 43,
                messageRegExp = "Unmapped target property: \"integerConstant\"")
        }
    )
    public void errorOnSourceAndExpression() throws ParseException {
    }

    @Test
    @IssueKey("255")
    @WithClasses({
        Source1.class,
        Source2.class,
        Target2.class,
        SourceTargetMapperSeveralSources.class
    })
    public void shouldMapSameSourcePropertyToSeveralTargetPropertiesFromSeveralSources() throws ParseException {
        Source1 source1 = new Source1();
        source1.setSomeProp( "someProp" );

        Source2 source2 = new Source2();
        source2.setAnotherProp( "anotherProp" );
        Target2 target = SourceTargetMapperSeveralSources.INSTANCE.sourceToTarget( source1, source2 );

        assertThat( target ).isNotNull();
        assertThat( target.getSomeProp() ).isEqualTo( "someProp" );
        assertThat( target.getAnotherProp() ).isEqualTo( "anotherProp" );
        assertThat( target.getSomeConstant() ).isEqualTo( "stringConstant" );
    }

    @Test
    @IssueKey("700")
    @WithClasses({
        Source.class,
        Target.class,
        CountryEnum.class,
        ErroneousMapper5.class,
        StringListMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper5.class,
                kind = Kind.ERROR,
                line = 43,
                messageRegExp = "^Constant \"DENMARK\" doesn't exist in enum type org.mapstruct.ap.test.source."
                    + "constants.CountryEnum for property \"country\".$"),
            @Diagnostic(type = ErroneousMapper5.class,
                kind = Kind.ERROR,
                line = 41,
                messageRegExp = "^Can't map \"java.lang.String \"DENMARK\"\" to \"org.mapstruct.ap.test.source."
                    + "constants.CountryEnum country\".$")
        }
    )
    public void errorOnNonExistingEnumConstant() throws ParseException {
    }

    @Test
    @IssueKey("1401")
    @WithClasses({
        Source.class,
        Target.class,
        CountryEnum.class,
        ErroneousMapper6.class,
        StringListMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper6.class,
                kind = Kind.ERROR,
                line = 38,
                messageRegExp = "^.*Can't map \"java.lang.String \"3001\"\" to \"java.lang.Long "
                    + "longWrapperConstant\".*$")
        }
    )
    public void cannotMapIntConstantToLong() throws ParseException {
    }

    private Date getDate(String format, String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat( format );
        Date result = dateFormat.parse( date );
        return result;
    }

}
