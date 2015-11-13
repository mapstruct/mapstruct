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
package org.mapstruct.ap.test.template;


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
@IssueKey("383")
@WithClasses({ Source.class, NestedSource.class, Target.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class InheritConfigurationTest {

    @Test
    @WithClasses({ SourceTargetMapperSingle.class })
    public void shouldInheritConfigurationSingleCandidates() {

        Source source = new Source();
        source.setStringPropX( "1" );
        source.setIntegerPropX( 2 );
        source.setNestedSourceProp( new NestedSource("nested") );

        Target createdTarget = SourceTargetMapperSingle.INSTANCE.forwardCreate( source );
        assertThat( createdTarget ).isNotNull();
        assertThat( createdTarget.getStringPropY() ).isEqualTo( "1" );
        assertThat( createdTarget.getIntegerPropY() ).isEqualTo( 2 );
        assertThat( createdTarget.getNestedResultProp() ).isEqualTo( "nested" );
        assertThat( createdTarget.getExpressionProp() ).isEqualTo( "expression" );
        assertThat( createdTarget.getConstantProp() ).isEqualTo( "constant" );

        Target updatedTarget = new Target();
        SourceTargetMapperSingle.INSTANCE.forwardUpdate( source, updatedTarget );
        assertThat( updatedTarget ).isNotNull();
        assertThat( updatedTarget.getStringPropY() ).isEqualTo( "1" );
        assertThat( updatedTarget.getIntegerPropY() ).isEqualTo( 2 );
        assertThat( updatedTarget.getNestedResultProp() ).isEqualTo( "nested" );
        assertThat( updatedTarget.getExpressionProp() ).isEqualTo( "expression" );
        assertThat( updatedTarget.getConstantProp() ).isEqualTo( "constant" );
    }

    @Test
    @WithClasses( { SourceTargetMapperMultiple.class } )
    public void shouldInheritConfigurationMultipleCandidates() {

        Source source = new Source();
        source.setStringPropX( "1" );
        source.setIntegerPropX( 2 );
        source.setNestedSourceProp( new NestedSource("nested") );

        Target createdTarget = SourceTargetMapperMultiple.INSTANCE.forwardCreate( source );
        assertThat( createdTarget ).isNotNull();
        assertThat( createdTarget.getStringPropY() ).isEqualTo( "1" );
        assertThat( createdTarget.getIntegerPropY() ).isEqualTo( 2 );
        assertThat( createdTarget.getNestedResultProp() ).isEqualTo( "nested" );
        assertThat( createdTarget.getExpressionProp() ).isEqualTo( "expression" );
        assertThat( createdTarget.getConstantProp() ).isEqualTo( "constant" );

        Target updatedTarget = new Target();
        SourceTargetMapperMultiple.INSTANCE.forwardUpdate( source, updatedTarget );
        assertThat( updatedTarget ).isNotNull();
        assertThat( updatedTarget.getStringPropY() ).isEqualTo( "1" );
        assertThat( updatedTarget.getIntegerPropY() ).isEqualTo( 2 );
        assertThat( updatedTarget.getNestedResultProp() ).isEqualTo( "nested" );
        assertThat( updatedTarget.getExpressionProp() ).isEqualTo( "expression" );
        assertThat( updatedTarget.getConstantProp() ).isEqualTo( "constant" );

    }

   @Test
    @WithClasses({ SourceTargetMapperSeveralArgs.class })
    public void shouldInheritConfigurationSeveralArgs() {

        Source source = new Source();
        source.setStringPropX( "1" );
        source.setIntegerPropX( 2 );
        source.setNestedSourceProp( new NestedSource("nested") );

        Target createdTarget = SourceTargetMapperSeveralArgs.INSTANCE.forwardCreate( source, "constant", "expression" );
        assertThat( createdTarget ).isNotNull();
        assertThat( createdTarget.getStringPropY() ).isEqualTo( "1" );
        assertThat( createdTarget.getIntegerPropY() ).isEqualTo( 2 );
        assertThat( createdTarget.getNestedResultProp() ).isEqualTo( "nested" );
        assertThat( createdTarget.getExpressionProp() ).isEqualTo( "expression" );
        assertThat( createdTarget.getConstantProp() ).isEqualTo( "constant" );

        Target updatedTarget = new Target();
        SourceTargetMapperSeveralArgs.INSTANCE.forwardUpdate( source, "constant", "expression", updatedTarget );
        assertThat( updatedTarget ).isNotNull();
        assertThat( updatedTarget.getStringPropY() ).isEqualTo( "1" );
        assertThat( updatedTarget.getIntegerPropY() ).isEqualTo( 2 );
        assertThat( updatedTarget.getNestedResultProp() ).isEqualTo( "nested" );
        assertThat( updatedTarget.getExpressionProp() ).isEqualTo( "expression" );
        assertThat( updatedTarget.getConstantProp() ).isEqualTo( "constant" );

    }

    @Test
    @WithClasses({ SourceTargetMapperAmbiguous1.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapperAmbiguous1.class,
                kind = Kind.ERROR,
                line = 54,
                messageRegExp = "Several matching methods exist: forwardCreate\\(\\), "
                    + "forwardCreate1\\(\\). Specify a name explicitly."),
            @Diagnostic(type = SourceTargetMapperAmbiguous1.class,
                kind = Kind.WARNING,
                line = 55,
                messageRegExp = "Unmapped target properties: \"stringPropY, integerPropY, constantProp, "
                    + "expressionProp, nestedResultProp\"")
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
                line = 54,
                messageRegExp = "None of the candidates forwardCreate\\(\\), forwardCreate1\\(\\) matches given "
                    + "name: \"blah\"."),
            @Diagnostic(type = SourceTargetMapperAmbiguous2.class,
                kind = Kind.WARNING,
                line = 55,
                messageRegExp = "Unmapped target properties: \"stringPropY, integerPropY, constantProp, "
                    + "expressionProp, nestedResultProp\"")
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
                line = 54,
                messageRegExp = "Given name \"forwardCreate\" matches several candidate methods: "
                    + ".*forwardCreate.*, .*forwardCreate.*"),
            @Diagnostic(type = SourceTargetMapperAmbiguous3.class,
                kind = Kind.WARNING,
                line = 55,
                messageRegExp = "Unmapped target properties: \"stringPropY, integerPropY, constantProp, "
                        + "expressionProp, nestedResultProp\"")        }
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
                line = 45,
                messageRegExp = "Given name \"blah\" does not match the only candidate. Did you mean: "
                    + "\"forwardCreate\"."),
            @Diagnostic(type = SourceTargetMapperNonMatchingName.class,
                        kind = Kind.WARNING,
                        line = 46,
                messageRegExp = "Unmapped target properties: \"stringPropY, integerPropY, constantProp, "
                    + "expressionProp, nestedResultProp\"")
        }
    )
    public void shouldAdviseOnSpecifyingCorrectName() {
    }
}
