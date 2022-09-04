/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.typebounds;

import static org.assertj.core.api.Assertions.assertThat;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Ben Zegveld
 */
@IssueKey( "2947" )
@WithClasses( { Source.class, Target.class, WildcardedInterface.class } )
class WildcardTypeBoundsTest {

    @ProcessorTest
    @WithClasses( { WildcardExtendsMapper.class } )
    void mapsWithWildcardSuccesfully() {
        Source<WildcardedInterfaceImpl> source = new Source<>();
        source.setObject( new WildcardedInterfaceImpl() );
        source.getObject().setContents( "Test contents" );

        Target target = WildcardExtendsMapper.INSTANCE.map( source );

        assertThat( target.getObject() ).isEqualTo( "Test contents" );
    }

    @ProcessorTest
    @WithClasses( { WildcardConditionalExtendsMapper.class } )
    void mapsWithWildcardConditionTrue() {
        Source<WildcardedInterfaceImpl> source = new Source<>();
        WildcardedInterfaceImpl sourceImpl = new WildcardedInterfaceImpl();
        source.setObject( sourceImpl );
        sourceImpl.setContents( "Test contents" );
        sourceImpl.setShouldMap( false );

        Target target = WildcardConditionalExtendsMapper.INSTANCE.map( source );

        assertThat( target.getObject() ).isBlank();
    }

    @ProcessorTest
    @WithClasses( { WildcardConditionalExtendsMapper.class } )
    void mapsWithWildcardConditionFalse() {
        Source<WildcardedInterfaceImpl> source = new Source<>();
        WildcardedInterfaceImpl sourceImpl = new WildcardedInterfaceImpl();
        source.setObject( sourceImpl );
        sourceImpl.setContents( "Test contents" );
        sourceImpl.setShouldMap( true );

        Target target = WildcardConditionalExtendsMapper.INSTANCE.map( source );

        assertThat( target.getObject() ).isEqualTo( "Test contents" );
    }

    @ProcessorTest
    @WithClasses( { WildcardNestedExtendsMapper.class, SourceContainer.class } )
    void mapsWithNestedWildcardSuccessfully() {
        Source<WildcardedInterfaceImpl> source = new Source<>();
        source.setObject( new WildcardedInterfaceImpl() );
        source.getObject().setContents( "Test contents" );
        SourceContainer sourceContainer = new SourceContainer();
        sourceContainer.setContained( source );

        Target target = WildcardNestedExtendsMapper.INSTANCE.map( sourceContainer );

        assertThat( target.getObject() ).isEqualTo( "Test contents" );
    }

    @ProcessorTest
    @WithClasses( { WildcardNestedInheritedExtendsMapper.class, SourceContainerInherited.class } )
    void mapsWithNestedInheritedWildcardSuccessfully() {
        Source<WildcardedInterfaceImpl> source = new Source<>();
        source.setObject( new WildcardedInterfaceImpl() );
        source.getObject().setContents( "Test contents" );
        SourceContainerInherited<WildcardedInterfaceImpl> sourceContainer = new SourceContainerInherited<>();
        sourceContainer.setContained( source );

        Target target = WildcardNestedInheritedExtendsMapper.INSTANCE.map( sourceContainer );

        assertThat( target.getObject() ).isEqualTo( "Test contents" );
    }

    @ProcessorTest
    @WithClasses( { ErroneousTypeVarExtendsMapper.class, WildcardedInterfaceImpl.class } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = @Diagnostic(
            kind = Kind.ERROR,
            type = ErroneousTypeVarExtendsMapper.class,
            line = 20,
            message = "Can't generate mapping method for a generic type variable source."
        )
    )
    void mapsTypeVarExtendNotAllowed() {
    }
}
