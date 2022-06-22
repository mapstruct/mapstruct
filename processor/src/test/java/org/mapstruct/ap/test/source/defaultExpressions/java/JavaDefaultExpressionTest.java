/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.defaultExpressions.java;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Date;

import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jeffrey Smyth
 */
public class JavaDefaultExpressionTest {

    @ProcessorTest
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

    @ProcessorTest
    @WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
    public void testJavaDefaultExpressionWithNoValues() {
        Source source = new Source();

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getSourceId() ).isEqualTo( "test" );
        assertThat( target.getSourceDate() ).isEqualTo( new Date( 30L ) );
    }

    @ProcessorTest
    @WithClasses({ Source.class, Target.class, MultiLineDefaultExpressionMapper.class })
    public void testMultiLineJavaDefaultExpression() {
        Source source = new Source();

        Target target = MultiLineDefaultExpressionMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getSourceId() ).isEqualTo( "test" );
        assertThat( target.getSourceDate() )
            .isEqualTo( Date.from(
                LocalDate.of( 2022, Month.JUNE, 5 )
                    .atTime( 17, 10 )
                    .toInstant( ZoneOffset.UTC )
            ) );
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousDefaultExpressionExpressionMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 22,
                message = "Expression and default expression are both defined in @Mapping,"
                    + " either define an expression or a default expression."
            ),
            @Diagnostic(type = ErroneousDefaultExpressionExpressionMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 26,
                message = "Unmapped target property: \"sourceId\"."
            )
        }
    )
    @WithClasses({ Source.class, Target.class, ErroneousDefaultExpressionExpressionMapper.class })
    public void testJavaDefaultExpressionExpression() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousDefaultExpressionConstantMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 22,
                message = "Constant and default expression are both defined in @Mapping,"
                    + " either define a constant or a default expression."
            ),
            @Diagnostic(type = ErroneousDefaultExpressionConstantMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 25,
                message = "Unmapped target property: \"sourceId\"."
            )
        }
    )
    @WithClasses({ Source.class, Target.class, ErroneousDefaultExpressionConstantMapper.class })
    public void testJavaDefaultExpressionConstant() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousDefaultExpressionDefaultValueMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 22,
                message = "Default value and default expression are both defined in @Mapping,"
                    + " either define a default value or a default expression."
            ),
            @Diagnostic(type = ErroneousDefaultExpressionDefaultValueMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 25,
                message = "Unmapped target property: \"sourceId\"."
            )
        }
    )
    @WithClasses({ Source.class, Target.class, ErroneousDefaultExpressionDefaultValueMapper.class })
    public void testJavaDefaultExpressionDefaultValue() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousDefaultExpressionMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 22,
                message = "Value for default expression must be given in the form \"java(<EXPRESSION>)\"."
            )
        }
    )
    @WithClasses({ Source.class, Target.class, ErroneousDefaultExpressionMapper.class })
    public void testJavaDefaultExpressionInvalidExpression() {
    }
}
