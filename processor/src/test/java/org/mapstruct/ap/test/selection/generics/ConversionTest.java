/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

import java.math.BigDecimal;

import org.mapstruct.ap.test.NoProperties;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the invocation of generic methods for mapping bean properties.
 *
 * @author Sjaak Derksen
 */
@WithClasses({
    GenericTypeMapper.class, Wrapper.class, ArrayWrapper.class, TwoArgHolder.class, TwoArgWrapper.class,
    UpperBoundWrapper.class, WildCardExtendsWrapper.class, WildCardSuperWrapper.class, WildCardExtendsMBWrapper.class,
    TypeA.class, TypeB.class, TypeC.class
})
@IssueKey(value = "79")
public class ConversionTest {

    @ProcessorTest
    @WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
    public void shouldApplyGenericTypeMapper() {

        // setup used types
        TypeB typeB = new TypeB();
        TypeC typeC = new TypeC();

        // setup source
        Source source = new Source();
        source.setFooInteger( new Wrapper<>( 5 ) );
        source.setFooString( new Wrapper<>( "test" ) );
        source.setFooStringArray( new Wrapper<>( new String[] { "test1", "test2" } ) );
        source.setFooLongArray( new ArrayWrapper<>( new Long[] { 5L, 3L } ) );
        source.setFooTwoArgs( new TwoArgWrapper<>( new TwoArgHolder<>( 3, true ) ) );
        source.setFooNested( new Wrapper<>( new Wrapper<>( new BigDecimal( 5 ) ) ) );
        source.setFooUpperBoundCorrect( new UpperBoundWrapper<>( typeB ) );
        source.setFooWildCardExtendsString( new WildCardExtendsWrapper<>( "test3" ) );
        source.setFooWildCardExtendsTypeCCorrect( new WildCardExtendsWrapper<>( typeC ) );
        source.setFooWildCardExtendsTypeBCorrect( new WildCardExtendsWrapper<>( typeB ) );
        source.setFooWildCardSuperString( new WildCardSuperWrapper<>( "test4" ) );
        source.setFooWildCardExtendsMBTypeCCorrect( new WildCardExtendsMBWrapper<>( typeC ) );
        source.setFooWildCardSuperTypeBCorrect( new WildCardSuperWrapper<>( typeB ) );

        // define wrapper
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        // assert results
        assertThat( target ).isNotNull();
        assertThat( target.getFooInteger() ).isEqualTo( 5 );
        assertThat( target.getFooString() ).isEqualTo( "test" );
        assertThat( target.getFooStringArray() ).isEqualTo( new String[] { "test1", "test2" } );
        assertThat( target.getFooLongArray() ).isEqualTo( new Long[] { 5L, 3L } );
        assertThat( target.getFooTwoArgs().getArg1() ).isEqualTo( 3 );
        assertThat( target.getFooTwoArgs().getArg2() ).isEqualTo( true );
        assertThat( target.getFooNested() ).isEqualTo( new BigDecimal( 5 ) );
        assertThat( target.getFooUpperBoundCorrect() ).isEqualTo( typeB );
        assertThat( target.getFooWildCardExtendsString() ).isEqualTo( "test3" );
        assertThat( target.getFooWildCardExtendsTypeCCorrect() ).isEqualTo( typeC );
        assertThat( target.getFooWildCardExtendsTypeBCorrect() ).isEqualTo( typeB );
        assertThat( target.getFooWildCardSuperString() ).isEqualTo( "test4" );
        assertThat( target.getFooWildCardExtendsMBTypeCCorrect() ).isEqualTo( typeC );
        assertThat( target.getFooWildCardSuperTypeBCorrect() ).isEqualTo( typeB );

    }

    @ProcessorTest
    @WithClasses({ ErroneousSource1.class, ErroneousTarget1.class, ErroneousSourceTargetMapper1.class })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousSourceTargetMapper1.class,
                kind = javax.tools.Diagnostic.Kind.ERROR, line = 16,
                messageRegExp = "No target bean properties found: can't map property \"org.mapstruct.ap.test.selection."
                  + "generics.UpperBoundWrapper<org.mapstruct.ap.test.selection.generics.TypeA> "
                  + "fooUpperBoundFailure\" to \"org.mapstruct.ap.test.selection.generics.TypeA fooUpperBoundFailure\"")
        })
    public void shouldFailOnUpperBound() {
    }

    @ProcessorTest
    @WithClasses({ ErroneousSource2.class, ErroneousTarget2.class, ErroneousSourceTargetMapper2.class })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousSourceTargetMapper2.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                messageRegExp = "No target bean properties found: can't map property "
                    + "\"org.mapstruct.ap.test.selection.generics.WildCardExtendsWrapper"
                    + "<org.mapstruct.ap.test.selection.generics.TypeA> fooWildCardExtendsTypeAFailure\" to"
                    + " \"org.mapstruct.ap.test.selection.generics.TypeA fooWildCardExtendsTypeAFailure\"")
        })
    public void shouldFailOnWildCardBound() {
    }

    @ProcessorTest
    @WithClasses({ ErroneousSource3.class, ErroneousTarget3.class, ErroneousSourceTargetMapper3.class })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousSourceTargetMapper3.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                messageRegExp = "No target bean properties found: can't map property "
                    + "\"org.mapstruct.ap.test.selection.generics."
                    + "WildCardExtendsMBWrapper<org.mapstruct.ap.test.selection.generics.TypeB> "
                    + "fooWildCardExtendsMBTypeBFailure\" to \"org.mapstruct.ap.test.selection.generics.TypeB "
                    + "fooWildCardExtendsMBTypeBFailure\"")
        })
    public void shouldFailOnWildCardMultipleBounds() {
    }

    @ProcessorTest
    @WithClasses({ ErroneousSource4.class, ErroneousTarget4.class, ErroneousSourceTargetMapper4.class })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousSourceTargetMapper4.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                messageRegExp = "No target bean properties found: can't map property "
                    + "\"org.mapstruct.ap.test.selection.generics.WildCardSuperWrapper"
                    + "<org.mapstruct.ap.test.selection.generics.TypeA> fooWildCardSuperTypeAFailure\" to"
                    + " \"org.mapstruct.ap.test.selection.generics.TypeA fooWildCardSuperTypeAFailure\"")
        })
    public void shouldFailOnSuperBounds1() {
    }

    @ProcessorTest
    @WithClasses({ ErroneousSource5.class, ErroneousTarget5.class, ErroneousSourceTargetMapper5.class })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousSourceTargetMapper5.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                messageRegExp = "No target bean properties found: can't map property "
                    + "\"org.mapstruct.ap.test.selection.generics.WildCardSuperWrapper"
                    + "<org.mapstruct.ap.test.selection.generics.TypeC> fooWildCardSuperTypeCFailure\" to"
                    + " \"org.mapstruct.ap.test.selection.generics.TypeC fooWildCardSuperTypeCFailure\"")
        })
    public void shouldFailOnSuperBounds2() {
    }

    @ProcessorTest
    @WithClasses({
        ErroneousSource6.class,
        ErroneousTarget6.class,
        ErroneousSourceTargetMapper6.class,
        NoProperties.class
    })
    @ExpectedCompilationOutcome( value = CompilationResult.FAILED, diagnostics = {
        @Diagnostic( type = ErroneousSourceTargetMapper6.class, kind = javax.tools.Diagnostic.Kind.ERROR,
                        line = 16, messageRegExp =
                        ".*\\.generics\\.WildCardSuperWrapper<.*\\.generics\\.TypeA> does not have an "
                                        + "accessible parameterless constructor\\." ),
        @Diagnostic( type = ErroneousSourceTargetMapper6.class, kind = javax.tools.Diagnostic.Kind.ERROR,
                        line = 16, messageRegExp =
                        "No target bean properties found: can't map property \".*NoProperties "
                                        + "foo\\.wrapped\" to"
                                        + " \"org.mapstruct.ap.test.selection.generics.TypeA "
                                        + "foo\\.wrapped\"" )
    } )
    public void shouldFailOnNonMatchingWildCards() {
    }
}
