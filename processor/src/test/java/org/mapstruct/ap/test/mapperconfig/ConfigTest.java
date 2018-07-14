/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mapperconfig;

import static org.assertj.core.api.Assertions.assertThat;

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
@IssueKey( "102" )
@WithClasses( {
    Source.class,
    Target.class,
    FooDto.class,
    FooEntity.class,
    CentralConfig.class,
    CustomMapperViaMapper.class,
    CustomMapperViaMapperConfig.class,
    SourceTargetMapper.class
} )
@RunWith(AnnotationProcessorTestRunner.class)
public class ConfigTest {

    @Test
    @WithClasses( { Target.class, SourceTargetMapper.class } )
    public void shouldUseCustomMapperViaMapperForFooToEntity() {

        Target target = SourceTargetMapper.INSTANCE.toTarget( new Source( new FooDto() ) );
        assertThat( target.getFoo() ).isNotNull();
        assertThat( target.getFoo().getCreatedBy() ).isEqualTo( CustomMapperViaMapper.class.getSimpleName() );
    }

    @Test
    @WithClasses( { Target.class, SourceTargetMapper.class } )
    public void shouldUseCustomMapperViaMapperConfigForFooToDto() {

        Source source = SourceTargetMapper.INSTANCE.toSource( new Target( new FooEntity() ) );
        assertThat( source.getFoo() ).isNotNull();
        assertThat( source.getFoo().getCreatedBy() ).isEqualTo( CustomMapperViaMapperConfig.class.getSimpleName() );
    }

    @Test
    @WithClasses( { TargetNoFoo.class, SourceTargetMapperWarn.class } )
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapperWarn.class,
                kind = javax.tools.Diagnostic.Kind.WARNING, line = 37,
                messageRegExp = "Unmapped target property: \"noFoo\"")
        })
    public void shouldUseWARNViaMapper() {
    }

    @Test
    @WithClasses( { TargetNoFoo.class, SourceTargetMapperErroneous.class } )
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapperErroneous.class,
                kind = javax.tools.Diagnostic.Kind.ERROR, line = 33,
                messageRegExp = "Unmapped target property: \"noFoo\"")
        })
    public void shouldUseERRORViaMapperConfig() {
    }
}
