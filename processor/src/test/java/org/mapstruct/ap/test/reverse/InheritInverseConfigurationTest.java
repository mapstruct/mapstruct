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
package org.mapstruct.ap.test.reverse;

import javax.tools.Diagnostic.Kind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Sjaak Derksen
 */
@IssueKey("252")
@WithClasses({ Source.class, Target.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class InheritInverseConfigurationTest {

    @Test
    @WithClasses({ SourceTargetMapper.class })
    public void shouldInheritInverseConfigurationMultipleCandidates() {

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
    @WithClasses({ SourceTargetMapperAmbiguous1.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapperAmbiguous1.class,
                kind = Kind.ERROR,
                line = 49,
                messageRegExp = "Several matching inverse methods exist: forward\\(\\), "
                    + "forwardNotToReverse\\(\\). Specify a name explicitly."),
            @Diagnostic(type = SourceTargetMapperAmbiguous1.class,
                kind = Kind.WARNING,
                line = 54,
                messageRegExp = "Unmapped target properties: \"stringPropX, integerPropX\"")
        }
    )
    public void shouldRaiseAmbiguousReverseMethodError() {
    }

    @Test
    @WithClasses({ SourceTargetMapperAmbiguous2.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapperAmbiguous2.class,
                kind = Kind.ERROR,
                line = 49,
                messageRegExp = "None of the candidates forward\\(\\), forwardNotToReverse\\(\\) matches given "
                    + "name: \"blah\"."),
            @Diagnostic(type = SourceTargetMapperAmbiguous2.class,
                kind = Kind.WARNING,
                line = 54,
                messageRegExp = "Unmapped target properties: \"stringPropX, integerPropX\"")
        }
    )
    public void shouldRaiseAmbiguousReverseMethodErrorWrongName() {
    }

    @Test
    @WithClasses({ SourceTargetMapperAmbiguous3.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapperAmbiguous3.class,
                kind = Kind.ERROR,
                line = 50,
                messageRegExp = "Given name \"forward\" matches several candidate methods: .*forward\\(.+\\), "
                    + ".*forward\\(.+\\)"),
            @Diagnostic(type = SourceTargetMapperAmbiguous3.class,
                kind = Kind.WARNING,
                line = 55,
                messageRegExp = "Unmapped target properties: \"stringPropX, integerPropX\"")
        }
    )
    public void shouldRaiseAmbiguousReverseMethodErrorDuplicatedName() {
    }

    @Test
    @WithClasses({ SourceTargetMapperNonMatchingName.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapperNonMatchingName.class,
                kind = Kind.ERROR,
                line = 42,
                messageRegExp = "Given name \"blah\" does not match the only candidate. Did you mean: "
                    + "\"forward\"."),
            @Diagnostic(type = SourceTargetMapperNonMatchingName.class,
                kind = Kind.WARNING,
                line = 47,
                messageRegExp = "Unmapped target properties: \"stringPropX, integerPropX\"")
        }
    )
    public void shouldAdviseOnSpecifyingCorrectName() {
    }

}
