/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.expressions.java;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.mapstruct.ap.test.source.expressions.java.mapper.TimeAndFormat;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Sjaak Derksen
 */
public class JavaExpressionTest {

   @ProcessorTest
   @WithClasses({ Source.class, Target.class, TimeAndFormat.class, SourceTargetMapper.class })
    public void testJavaExpressionInsertion() throws ParseException {
        Source source = new Source();
        String format = "dd-MM-yyyy,hh:mm:ss";
        Date time = getTime( format, "09-01-2014,01:35:03" );

        source.setFormat( format );
        source.setTime( time );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getTimeAndFormat().getTime() ).isEqualTo( time );
        assertThat( target.getTimeAndFormat().getFormat() ).isEqualTo( format );
        assertThat( target.getAnotherProp() ).isNull();
    }

    @IssueKey( "255" )
    @ProcessorTest
    @WithClasses({
        Source.class,
        Source2.class,
        Target.class,
        TimeAndFormat.class,
        SourceTargetMapperSeveralSources.class
      })
    public void testJavaExpressionInsertionWithSeveralSources() throws ParseException {
        Source source1 = new Source();
        String format = "dd-MM-yyyy,hh:mm:ss";
        Date time = getTime( format, "09-01-2014,01:35:03" );

        source1.setFormat( format );
        source1.setTime( time );

        Source2 source2 = new Source2();
        source2.setAnotherProp( "test" );

        Target target = SourceTargetMapperSeveralSources.INSTANCE.sourceToTarget( source1, source2 );

        assertThat( target ).isNotNull();
        assertThat( target.getTimeAndFormat().getTime() ).isEqualTo( time );
        assertThat( target.getTimeAndFormat().getFormat() ).isEqualTo( format );
        assertThat( target.getAnotherProp() ).isEqualTo( "test" );
    }

    private Date getTime(String format, String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat( format );
        return dateFormat.parse( date );
    }

   @ProcessorTest
   @WithClasses({ Source.class, Target.class, TimeAndFormat.class, SourceTargetMapper.class })
    public void testJavaExpressionInsertionWithExistingTarget() throws ParseException {
        Source source = new Source();
        String format = "dd-MM-yyyy,hh:mm:ss";
        Date time = getTime( format, "09-01-2014,01:35:03" );

        source.setFormat( format );
        source.setTime( time );
        Target target = new Target();

        Target target2 = SourceTargetMapper.INSTANCE.sourceToTargetWithMappingTarget( source, target );

        assertThat( target ).isNotNull();
        assertThat( target.getTimeAndFormat().getTime() ).isEqualTo( time );
        assertThat( target.getTimeAndFormat().getFormat() ).isEqualTo( format );
        assertThat( target.getAnotherProp() ).isNull();
        assertThat( target ).isEqualTo( target2 );
    }

    @IssueKey( "278" )
    @ProcessorTest
    @WithClasses({
        SourceBooleanWorkAround.class,
        TargetBooleanWorkAround.class,
        BooleanWorkAroundMapper.class
      })
    public void testBooleanGetterWorkAround() throws ParseException {
        SourceBooleanWorkAround source = new SourceBooleanWorkAround();
        source.setVal( Boolean.TRUE );

        TargetBooleanWorkAround target = BooleanWorkAroundMapper.INSTANCE.mapST( source );
        assertThat( target ).isNotNull();
        assertThat( target.isVal() ).isTrue();

        SourceBooleanWorkAround source2 = BooleanWorkAroundMapper.INSTANCE.mapTS( target );
        assertThat( source2 ).isNotNull();
        assertThat( source2.isVal() ).isTrue();
    }

    @IssueKey( "305" )
    @ProcessorTest
    @WithClasses({
        SourceList.class,
        TargetList.class,
        SourceTargetListMapper.class
    })
    public void testGetterOnly() throws ParseException {
        SourceList source = new SourceList();
        source.setList( Arrays.asList( "test1" ) );

        TargetList target = SourceTargetListMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getList() ).isEqualTo( Arrays.asList( "test2" ) );
    }

    @ProcessorTest
    @WithClasses({ Source.class, Target.class, TimeAndFormat.class, MultiLineExpressionMapper.class })
    public void testMultiLineJavaExpressionInsertion() throws ParseException {
        Source source = new Source();
        String format = "dd-MM-yyyy,hh:mm:ss";
        Date time = getTime( format, "09-01-2014,01:35:03" );

        source.setFormat( format );
        source.setTime( time );

        Target target = MultiLineExpressionMapper.INSTANCE.mapUsingMultiLineExpression( source );

        assertThat( target ).isNotNull();
        assertThat( target.getTimeAndFormat().getTime() ).isEqualTo( time );
        assertThat( target.getTimeAndFormat().getFormat() ).isEqualTo( format );
        assertThat( target.getAnotherProp() ).isNull();

        target = MultiLineExpressionMapper.INSTANCE.mapUsingMultiLineExpressionWithLeadingSpaces( source );

        assertThat( target ).isNotNull();
        assertThat( target.getTimeAndFormat().getTime() ).isEqualTo( time );
        assertThat( target.getTimeAndFormat().getFormat() ).isEqualTo( format );
        assertThat( target.getAnotherProp() ).isNull();
    }

    @IssueKey( "1851" )
    @ProcessorTest
    @WithClasses({
        Source.class,
        Target.class,
        QualifierProvider.class,
        TimeAndFormat.class,
        ErroneousSourceTargetMapperExpressionAndQualifiers.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousSourceTargetMapperExpressionAndQualifiers.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 18,
                message = "Expression and a qualifier both defined in @Mapping," +
                    " either define an expression or a qualifier."
            ),
            @Diagnostic(type = ErroneousSourceTargetMapperExpressionAndQualifiers.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 24,
                message = "Expression and a qualifier both defined in @Mapping," +
                    " either define an expression or a qualifier."
            )
        }
    )
    public void testExpressionAndQualifiedDoesNotCompile() {
    }
}
