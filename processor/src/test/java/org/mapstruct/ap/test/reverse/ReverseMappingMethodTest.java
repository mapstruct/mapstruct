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
package org.mapstruct.ap.test.reverse;

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
 * @author Sjaak Derksen
 *
 */
@IssueKey( "252" )
@WithClasses( { Source.class, Target.class } )
@RunWith( AnnotationProcessorTestRunner.class )
public class ReverseMappingMethodTest {

    @Test
    @WithClasses( { SourceTargetMapper.class } )
    public void shouldReverseMappingMethodMultipleCandidates() {

        Source source = new Source();
        source.setPropertyToIgnoreDownstream( "propToIgnoreDownStream" );
        source.setStringPropX( "1" );
        source.setIntegerPropX( 2 );

        Target target = SourceTargetMapper.INSTANCE.forward( source );
        assertThat( target ).isNotNull();
        assertThat( target.getStringPropY() ).isEqualTo( "1" );
        assertThat( target.getIntegerPropY() ).isEqualTo( 2 );
        assertThat( target.getPropertyNotToIgnoreUpstream() ).isEqualTo( "propToIgnoreDownStream" );

        source = SourceTargetMapper.INSTANCE.reverse( target );
        assertThat( source ).isNotNull();
        assertThat( source.getStringPropX() ).isEqualTo( "1" );
        assertThat( source.getIntegerPropX() ).isEqualTo( 2 );
        assertThat( source.getSomeConstantDownstream() ).isEqualTo( "test" );
        assertThat( source.getPropertyToIgnoreDownstream() ).isNull();

    }

    @Test
    @WithClasses( { SourceTargetMapperAmbigious1.class } )
    @ExpectedCompilationOutcome(
             value = CompilationResult.FAILED,
            diagnostics = {
                @Diagnostic( type = SourceTargetMapperAmbigious1.class,
                        kind = Kind.ERROR,
                        line = 51,
                        messageRegExp = "None of the candidates \"forward,forwardNotToReverse\" matches. "
                        + "Consider specifiying 'configuredBy'." ),
                @Diagnostic( type = SourceTargetMapperAmbigious1.class,
                        kind = Kind.WARNING,
                        line = 56,
                        messageRegExp = "Unmapped target properties: \"stringPropX, integerPropX\"" )
            }
    )
    public void shouldRaiseAmbigousReverseMethodError() {
    }

    @Test
    @WithClasses( { SourceTargetMapperAmbigious2.class } )
    @ExpectedCompilationOutcome(
             value = CompilationResult.FAILED,
            diagnostics = {
                @Diagnostic( type = SourceTargetMapperAmbigious2.class,
                        kind = Kind.ERROR,
                        line = 51,
                        messageRegExp = "None of the candidates \"forward,forwardNotToReverse\", matches configuredBy: "
                                + "\"blah\"." ),
                @Diagnostic( type = SourceTargetMapperAmbigious2.class,
                        kind = Kind.WARNING,
                        line = 56,
                        messageRegExp = "Unmapped target properties: \"stringPropX, integerPropX\"" )
            }
    )
    public void shouldRaiseAmbigousReverseMethodErrorWrongName() {
    }

    @Test
    @WithClasses( { SourceTargetMapperAmbigious3.class } )
    @ExpectedCompilationOutcome(
             value = CompilationResult.FAILED,
            diagnostics = {
                @Diagnostic( type = SourceTargetMapperAmbigious3.class,
                        kind = Kind.ERROR,
                        line = 52,
                        messageRegExp = "ConfiguredBy:.*forward.*forward.*@MappingTarget.*matches more candidates:"
                                + " \"forward\"." ),
                @Diagnostic( type = SourceTargetMapperAmbigious3.class,
                        kind = Kind.WARNING,
                        line = 57,
                        messageRegExp = "Unmapped target properties: \"stringPropX, integerPropX\"" )
            }
    )
    public void shouldRaiseAmbigousReverseMethodErrorDuplicatedName() {
    }


    @Test
    @WithClasses( { SourceTargetMapperErroneouslyAnnotated.class } )
    @ExpectedCompilationOutcome(
             value = CompilationResult.FAILED,
            diagnostics = {
                @Diagnostic( type = SourceTargetMapperErroneouslyAnnotated.class,
                        kind = Kind.ERROR,
                        line = 51,
                        messageRegExp = "Resolved reverse mapping: \"reverse\" should not carry the "
                                + "@ReverseMappingMethod annotation itself." )
            }
    )
    public void shouldUseWronglyAnnotatedError() {
    }

    @Test
    @WithClasses( { SourceTargetMapperNonMatchingName.class } )
    @ExpectedCompilationOutcome(
             value = CompilationResult.FAILED,
            diagnostics = {
                @Diagnostic( type = SourceTargetMapperNonMatchingName.class,
                        kind = Kind.ERROR,
                        line = 44,
                        messageRegExp = "ConfiguredBy: \"blah\" does not match the only candidate. Did you mean: "
                                + "\"forward\"." ),
                @Diagnostic( type = SourceTargetMapperNonMatchingName.class,
                        kind = Kind.WARNING,
                        line = 49,
                        messageRegExp = "Unmapped target properties: \"stringPropX, integerPropX\"" )
            }
    )
    public void shouldAdviceOnSpecifyingCorrectName() {
    }

}
