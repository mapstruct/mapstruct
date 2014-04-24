/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.sourceconstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.tools.Diagnostic.Kind;
import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 *
 * @author Sjaak Derksen
 */

@RunWith(AnnotationProcessorTestRunner.class)
public class SourceConstantsTest {

    @Test
    @IssueKey( "187" )
    @WithClasses( {
        Source.class,
        Target.class,
        SourceTargetMapper.class,
        StringListMapper.class
    } )
    public void shouldMapSameSourcePropertyToSeveralTargetProperties() throws ParseException {
        Source source = new Source();
        source.setPropertyThatShouldBeMapped( "SomeProperty" );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getPropertyThatShouldBeMapped() ).isEqualTo( "SomeProperty" );
        assertThat( target.getStringConstant() ).isEqualTo( "stringConstant" );
        assertThat( target.getIntegerConstant() ).isEqualTo( 14 );
        assertThat( target.getLongWrapperConstant() ).isEqualTo( new Long(3001L) );
        assertThat( target.getDateConstant() ).isEqualTo( getDate( "dd-MM-yyyy", "09-01-2014") );
        assertThat( target.getNameConstants() ).isEqualTo( Arrays.asList( "jack", "jill", "tom" ) );
    }

    @Test
    @IssueKey( "187" )
    @WithClasses( {
        Source.class,
        Target.class,
        SourceTargetMapper.class,
        StringListMapper.class
    } )
    public void shouldMapTargetToSourceWithoutWhining() throws ParseException {
        Target target = new Target();
        target.setPropertyThatShouldBeMapped( "SomeProperty" );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( target.getPropertyThatShouldBeMapped() ).isEqualTo( "SomeProperty" );
    }

    @Test
    @IssueKey( "187" )
    @WithClasses( {
        Source.class,
        Target.class,
        ErroneousMapper1.class,
        StringListMapper.class
    } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper1.class,
                kind = Kind.ERROR,
                line = 42,
                messageRegExp = "Source and expression are both defined in Mapping, either define a source or an "
                        + "expression"),
            @Diagnostic(type = ErroneousMapper1.class,
                kind = Kind.WARNING,
                line = 42,
                messageRegExp = "Unmapped target property: \"integerConstant\"")
        }
    )
    public void errorOnSourceAndExpression() throws ParseException {
    }

    @Test
    @IssueKey( "187" )
    @WithClasses( {
        Source.class,
        Target.class,
        ErroneousMapper2.class,
        StringListMapper.class
    } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper2.class,
                kind = Kind.ERROR,
                line = 42,
                messageRegExp = "Either define a source or an expression in a Mapping"),
            @Diagnostic(type = ErroneousMapper2.class,
                kind = Kind.WARNING,
                line = 42,
                messageRegExp = "Unmapped target property: \"integerConstant\"")
        }
    )
    public void errorOnNeitherSourceNorExpression() throws ParseException {
    }

    private Date getDate(String format, String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat( format );
        Date result = dateFormat.parse( date );
        return result;
    }

}
