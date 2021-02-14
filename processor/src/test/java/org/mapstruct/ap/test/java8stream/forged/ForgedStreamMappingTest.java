/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.forged;

import javax.tools.Diagnostic.Kind;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for mappings between collection and stream types,
 *
 * @author Filip Hrisafov
 */
@IssueKey("962")
public class ForgedStreamMappingTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({ StreamMapper.class, Source.class, Target.class })
    public void shouldForgeNewIterableMappingMethod() {

        Source source = new Source();
        source.setFooStream( Collections.asSet( "1", "2" ).stream() );

        Target target = StreamMapper.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getFooStream() ).contains( 1L, 2L );

        Source source2 = StreamMapper.INSTANCE.targetToSource( target );
        assertThat( source2 ).isNotNull();
        assertThat( source2.getFooStream() ).contains( "1", "2" );

        generatedSource.forMapper( StreamMapper.class )
            .content()
            .as( "Mapper should not uas addAll" )
            .doesNotContain( "addAll( " )
            .as( "Mapper should not use Stream.empty()" )
            .doesNotContain( "Stream.empty()" );
    }

    @ProcessorTest
    @WithClasses({
        ErroneousStreamNonMappableStreamMapper.class,
        ErroneousNonMappableStreamSource.class,
        ErroneousNonMappableStreamTarget.class,
        Foo.class,
        Bar.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousStreamNonMappableStreamMapper.class,
                kind = Kind.ERROR,
                line = 17,
                message = "No target bean properties found: " +
                    "can't map Stream element \"Foo nonMappableStream\" to \"Bar nonMappableStream\". " +
                    "Consider to declare/implement a mapping method: \"Bar map(Foo value)\".")
        }
    )
    public void shouldGenerateNonMappableMethodForSetMapping() {
    }

    @ProcessorTest
    @WithClasses({ StreamMapper.class, Source.class, Target.class })
    public void shouldForgeNewIterableMappingMethodReturnNullOnNullSource() {

        Source source = new Source();
        source.setFooStream( null );
        source.setFooStream3( null );

        Target target = StreamMapper.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getFooStream() ).isNull();

        Source source2 = StreamMapper.INSTANCE.targetToSource( target );
        assertThat( source2 ).isNotNull();
        assertThat( source2.getFooStream() ).isNull();
        assertThat( source2.getFooStream3() ).isNull();
    }

    @ProcessorTest
    @WithClasses({ StreamMapperNullValueMappingReturnDefault.class, Source.class, Target.class })
    public void shouldForgeNewIterableMappingMethodReturnEmptyOnNullSource() {

        Source source = new Source();
        source.setFooStream( null );
        source.setFooStream3( null );

        Target target = StreamMapperNullValueMappingReturnDefault.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getFooStream() ).isEmpty();
        assertThat( target.getFooStream3() ).isEmpty();

        //The empty stream was already consumed so need to set a new one
        target.setFooStream3( null );

        Source source2 = StreamMapperNullValueMappingReturnDefault.INSTANCE.targetToSource( target );
        assertThat( source2 ).isNotNull();
        assertThat( source2.getFooStream() ).isEmpty();
        assertThat( source2.getFooStream3() ).isEmpty();
    }
}
