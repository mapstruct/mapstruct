package org.mapstruct.ap.test.source.defaultExpressions.java;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.source.defaultExpressions.java.mapper.DefaultExpressionConstantMapper;
import org.mapstruct.ap.test.source.defaultExpressions.java.mapper.DefaultExpressionDefaultValueMapper;
import org.mapstruct.ap.test.source.defaultExpressions.java.mapper.DefaultExpressionExpressionMapper;
import org.mapstruct.ap.test.source.defaultExpressions.java.mapper.InvalidDefaultExpressionMapper;
import org.mapstruct.ap.test.source.defaultExpressions.java.mapper.SourceTargetMapper;
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
@RunWith ( AnnotationProcessorTestRunner.class )
public class JavaDefaultExpressionTest {

    @Test
    @WithClasses ( {Source.class, Target.class, SourceTargetMapper.class} )
    public void testJavaDefaultExpressionWithValues() {
        Source source = new Source();
        source.setId( "TestId123" );
        source.setDate( new Date(  0L ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getSourceId() ).isEqualTo( source.getId() );
        assertThat( target.getSourceDate() ).isEqualTo( source.getDate() );
    }

    @Test
    @WithClasses ( {Source.class, Target.class, SourceTargetMapper.class} )
    public void testJavaDefaultExpressionWithNoValues() {
        Source source = new Source();

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getSourceId() ).isNotNull();
        assertThat( target.getSourceDate() ).isNotNull();
    }

    @Test
    @ExpectedCompilationOutcome (
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic (type = DefaultExpressionExpressionMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 25,
                messageRegExp = "Expression and default expression are both defined in @Mapping,"
                    + " either define an expression or a default expression."
            ),
            @Diagnostic (type = DefaultExpressionExpressionMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 25,
                messageRegExp = "Unmapped target property: \"sourceId\""
            )
        }
    )
    @WithClasses ( {Source.class, Target.class, DefaultExpressionExpressionMapper.class} )
    public void testJavaDefaultExpressionExpression() {
    }

    @Test
    @ExpectedCompilationOutcome (
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic (type = DefaultExpressionConstantMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 25,
                messageRegExp = "Constant and default expression are both defined in @Mapping,"
                                + " either define a constant or a default expression."
            ),
            @Diagnostic (type = DefaultExpressionConstantMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 25,
                messageRegExp = "Unmapped target property: \"sourceId\""
            )
        }
    )
    @WithClasses ( {Source.class, Target.class, DefaultExpressionConstantMapper.class} )
    public void testJavaDefaultExpressionConstant() {
    }

    @Test
    @ExpectedCompilationOutcome (
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic (type = DefaultExpressionDefaultValueMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 25,
                messageRegExp = "Default value and default expression are both defined in @Mapping,"
                                + " either define a default value or a default expression."
            ),
            @Diagnostic (type = DefaultExpressionDefaultValueMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 25,
                messageRegExp = "Unmapped target property: \"sourceId\""
            )
        }
    )
    @WithClasses ( {Source.class, Target.class, DefaultExpressionDefaultValueMapper.class} )
    public void testJavaDefaultExpressionDefaultValue() {
    }

    @Test
    @ExpectedCompilationOutcome (
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic (type = InvalidDefaultExpressionMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 22,
                messageRegExp = "Value for default expression must be given in the form \"java\\(<EXPRESSION>\\)\""
            )
        }
    )
    @WithClasses ( {Source.class, Target.class, InvalidDefaultExpressionMapper.class} )
    public void testJavaDefaultExpressionInvalidExpression() {
    }
}
