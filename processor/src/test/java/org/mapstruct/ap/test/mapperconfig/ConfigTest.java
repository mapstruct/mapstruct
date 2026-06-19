/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mapperconfig;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static java.lang.reflect.Modifier.isPrivate;
import static java.lang.reflect.Modifier.isProtected;
import static java.lang.reflect.Modifier.isPublic;
import static org.assertj.core.api.Assertions.assertThat;

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
public class ConfigTest {

    @ProcessorTest
    @WithClasses( { Target.class, SourceTargetMapper.class } )
    public void shouldUseCustomMapperViaMapperForFooToEntity() {

        Target target = SourceTargetMapper.INSTANCE.toTarget( new Source( new FooDto() ) );
        assertThat( target.getFoo() ).isNotNull();
        assertThat( target.getFoo().getCreatedBy() ).isEqualTo( CustomMapperViaMapper.class.getSimpleName() );
    }

    @ProcessorTest
    @WithClasses( { Target.class, SourceTargetMapper.class } )
    public void shouldUseCustomMapperViaMapperConfigForFooToDto() {

        Source source = SourceTargetMapper.INSTANCE.toSource( new Target( new FooEntity() ) );
        assertThat( source.getFoo() ).isNotNull();
        assertThat( source.getFoo().getCreatedBy() ).isEqualTo( CustomMapperViaMapperConfig.class.getSimpleName() );
    }

    @ProcessorTest
    @WithClasses( { Target.class, SourceTargetMapper.class } )
    public void shouldDeclareMapperImplementationAsPackagePrivate() throws ClassNotFoundException {
        Class<?> implementation = loadForMapper( SourceTargetMapper.class );
        assertThat( isDefault( implementation.getModifiers() )).isTrue();
    }

    @ProcessorTest
    @WithClasses( { TargetNoFoo.class, SourceTargetMapperWarn.class } )
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapperWarn.class,
                kind = javax.tools.Diagnostic.Kind.WARNING, line = 24,
                message = "Unmapped target property: \"noFoo\".")
        })
    public void shouldUseWARNViaMapper() {
    }

    @ProcessorTest
    @WithClasses( { TargetNoFoo.class, SourceTargetMapperErroneous.class } )
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapperErroneous.class,
                kind = javax.tools.Diagnostic.Kind.ERROR, line = 20,
                message = "Unmapped target property: \"noFoo\".")
        })
    public void shouldUseERRORViaMapperConfig() {
    }

    private static Class<?> loadForMapper(Class<?> mapper) throws ClassNotFoundException {
        return Thread.currentThread().getContextClassLoader().loadClass( mapper.getName() + "Impl" );
    }

    private static boolean isDefault(int modifiers) {
        return !isPublic( modifiers ) && !isProtected( modifiers ) && !isPrivate( modifiers );
    }
}
