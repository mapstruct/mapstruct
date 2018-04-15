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
package org.mapstruct.ap.test.source.defaultExpressions.java;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jeffrey Smyth
 */
@RunWith(AnnotationProcessorTestRunner.class)
public class JavaDefaultExpressionTest {

    @Test
    @WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
    public void testJavaDefaultExpressionWithValues() {
        Source source = new Source();
        source.setId( 123 );
        source.setDate( new Date( 0L ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getSourceId() ).isEqualTo( "123" );
        assertThat( target.getSourceDate() ).isEqualTo( source.getDate() );
    }

    @Test
    @WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
    public void testJavaDefaultExpressionWithNoValues() {
        Source source = new Source();

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getSourceId() ).isEqualTo( "test" );
        assertThat( target.getSourceDate() ).isEqualTo( new Date( 30L ) );
    }

    @Test
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousDefaultExpressionExpressionMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 35,
                messageRegExp = "Expression and default expression are both defined in @Mapping,"
                    + " either define an expression or a default expression."
            ),
            @Diagnostic(type = ErroneousDefaultExpressionExpressionMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 39,
                messageRegExp = "Unmapped target property: \"sourceId\""
            )
        }
    )
    @WithClasses({ Source.class, Target.class, ErroneousDefaultExpressionExpressionMapper.class })
    public void testJavaDefaultExpressionExpression() {
    }

    @Test
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousDefaultExpressionConstantMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 35,
                messageRegExp = "Constant and default expression are both defined in @Mapping,"
                    + " either define a constant or a default expression."
            ),
            @Diagnostic(type = ErroneousDefaultExpressionConstantMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 38,
                messageRegExp = "Unmapped target property: \"sourceId\""
            )
        }
    )
    @WithClasses({ Source.class, Target.class, ErroneousDefaultExpressionConstantMapper.class })
    public void testJavaDefaultExpressionConstant() {
    }

    @Test
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousDefaultExpressionDefaultValueMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 35,
                messageRegExp = "Default value and default expression are both defined in @Mapping,"
                    + " either define a default value or a default expression."
            ),
            @Diagnostic(type = ErroneousDefaultExpressionDefaultValueMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 38,
                messageRegExp = "Unmapped target property: \"sourceId\""
            )
        }
    )
    @WithClasses({ Source.class, Target.class, ErroneousDefaultExpressionDefaultValueMapper.class })
    public void testJavaDefaultExpressionDefaultValue() {
    }

    @Test
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousDefaultExpressionMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 35,
                messageRegExp = "Value for default expression must be given in the form \"java\\(<EXPRESSION>\\)\""
            )
        }
    )
    @WithClasses({ Source.class, Target.class, ErroneousDefaultExpressionMapper.class })
    public void testJavaDefaultExpressionInvalidExpression() {
    }
}
