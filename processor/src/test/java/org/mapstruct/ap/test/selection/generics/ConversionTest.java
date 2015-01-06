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
package org.mapstruct.ap.test.selection.generics;

import static org.fest.assertions.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

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
@RunWith(AnnotationProcessorTestRunner.class)
public class ConversionTest {

    @Test
    @WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
    public void shouldApplyGenericTypeMapper() {

        // setup used types
        TypeB typeB = new TypeB();
        TypeC typeC = new TypeC();

        // setup source
        Source source = new Source();
        source.setFooInteger( new Wrapper<Integer>( 5 ) );
        source.setFooString( new Wrapper<String>( "test" ) );
        source.setFooStringArray( new Wrapper<String[]>( new String[] { "test1", "test2" } ) );
        source.setFooLongArray( new ArrayWrapper<Long>( new Long[] { 5L, 3L } ) );
        source.setFooTwoArgs( new TwoArgWrapper<Integer, Boolean>( new TwoArgHolder<Integer, Boolean>( 3, true ) ) );
        source.setFooNested( new Wrapper<Wrapper<BigDecimal>>( new Wrapper<BigDecimal>( new BigDecimal( 5 ) ) ) );
        source.setFooUpperBoundCorrect( new UpperBoundWrapper<TypeB>( typeB ) );
        source.setFooWildCardExtendsString( new WildCardExtendsWrapper<String>( "test3" ) );
        source.setFooWildCardExtendsTypeCCorrect( new WildCardExtendsWrapper<TypeC>( typeC ) );
        source.setFooWildCardExtendsTypeBCorrect( new WildCardExtendsWrapper<TypeB>( typeB ) );
        source.setFooWildCardSuperString( new WildCardSuperWrapper<String>( "test4" ) );
        source.setFooWildCardExtendsMBTypeCCorrect( new WildCardExtendsMBWrapper<TypeC>( typeC ) );
        source.setFooWildCardSuperTypeBCorrect( new WildCardSuperWrapper<TypeB>( typeB ) );

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

    @Test
    @WithClasses({ ErroneousSource1.class, ErroneousTarget1.class, ErroneousSourceTargetMapper1.class })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousSourceTargetMapper1.class,
                kind = javax.tools.Diagnostic.Kind.ERROR, line = 29,
                messageRegExp = "Can't map property \"org.mapstruct.ap.test.selection.generics.UpperBoundWrapper"
                    + "<org.mapstruct.ap.test.selection.generics.TypeA> fooUpperBoundFailure\" to "
                    + "\"org.mapstruct.ap.test.selection.generics.TypeA fooUpperBoundFailure\"")
        })
    public void shouldFailOnUpperBound() {
    }

    @Test
    @WithClasses({ ErroneousSource2.class, ErroneousTarget2.class, ErroneousSourceTargetMapper2.class })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousSourceTargetMapper2.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 29,
                messageRegExp = "Can't map property \"org.mapstruct.ap.test.selection.generics.WildCardExtendsWrapper"
                    + "<org.mapstruct.ap.test.selection.generics.TypeA> fooWildCardExtendsTypeAFailure\" to"
                    + " \"org.mapstruct.ap.test.selection.generics.TypeA fooWildCardExtendsTypeAFailure\"")
        })
    public void shouldFailOnWildCardBound() {
    }

    @Test
    @WithClasses({ ErroneousSource3.class, ErroneousTarget3.class, ErroneousSourceTargetMapper3.class })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousSourceTargetMapper3.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 29,
                messageRegExp = "Can't map property \"org.mapstruct.ap.test.selection.generics."
                    + "WildCardExtendsMBWrapper<org.mapstruct.ap.test.selection.generics.TypeB> "
                    + "fooWildCardExtendsMBTypeBFailure\" to \"org.mapstruct.ap.test.selection.generics.TypeB "
                    + "fooWildCardExtendsMBTypeBFailure\"")
        })
    public void shouldFailOnWildCardMultipleBounds() {
    }

    @Test
    @WithClasses({ ErroneousSource4.class, ErroneousTarget4.class, ErroneousSourceTargetMapper4.class })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousSourceTargetMapper4.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 29,
                messageRegExp = "Can't map property \"org.mapstruct.ap.test.selection.generics.WildCardSuperWrapper"
                    + "<org.mapstruct.ap.test.selection.generics.TypeA> fooWildCardSuperTypeAFailure\" to"
                    + " \"org.mapstruct.ap.test.selection.generics.TypeA fooWildCardSuperTypeAFailure\"")
        })
    public void shouldFailOnSuperBounds1() {
    }

    @Test
    @WithClasses({ ErroneousSource5.class, ErroneousTarget5.class, ErroneousSourceTargetMapper5.class })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousSourceTargetMapper5.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 29,
                messageRegExp = "Can't map property \"org.mapstruct.ap.test.selection.generics.WildCardSuperWrapper"
                    + "<org.mapstruct.ap.test.selection.generics.TypeC> fooWildCardSuperTypeCFailure\" to"
                    + " \"org.mapstruct.ap.test.selection.generics.TypeC fooWildCardSuperTypeCFailure\"")
        })
    public void shouldFailOnSuperBounds2() {
    }

    @Test
    @WithClasses({ ErroneousSource6.class, ErroneousTarget6.class, ErroneousSourceTargetMapper6.class })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousSourceTargetMapper6.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 29,
                messageRegExp = "Can't map property \"org.mapstruct.ap.test.selection.generics.WildCardSuperWrapper"
                    + "<java.lang.String> foo\" to"
                    + " \"org.mapstruct.ap.test.selection.generics.WildCardSuperWrapper<java.lang.Integer> foo\"")
        })
    public void shouldFailOnNonMatchingWildCards() {
    }
}
