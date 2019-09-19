/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritance.complex;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import javax.tools.Diagnostic.Kind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for propagation of attributes inherited from super types.
 *
 * @author Andreas Gudian
 */
@WithClasses({
    Reference.class, SourceBase.class, SourceComposite.class,
    SourceExt.class, SourceExt2.class,
    TargetComposite.class, AdditionalFooSource.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class ComplexInheritanceTest {

    @Test
    @IssueKey("34")
    @WithClasses({ StandaloneSourceCompositeTargetCompositeMapper.class })
    public void shouldMapAttributesWithSuperTypeInStandaloneMapper() {
        SourceComposite source = createComposite();

        TargetComposite target = StandaloneSourceCompositeTargetCompositeMapper.INSTANCE.sourceToTarget( source );

        assertResult( target );

        assertThat( target.getProp4() ).isEqualTo( 999 );
        assertThat( target.getProp5() ).containsOnly( 42, 999 );
    }

    @Test
    @IssueKey("34")
    @WithClasses({ SourceCompositeTargetCompositeMapper.class, SourceBaseMappingHelper.class })
    public void shouldMapAttributesWithSuperTypeUsingOtherMapper() {
        SourceComposite source = createComposite();

        TargetComposite target = SourceCompositeTargetCompositeMapper.INSTANCE.sourceToTarget( source );

        assertResult( target );

        assertThat( target.getProp4() ).isEqualTo( 1000 );
        assertThat( target.getProp5() ).containsOnly( 43, 1000 );
    }

    @Test
    @IssueKey("34")
    @WithClasses({ ErroneousSourceCompositeTargetCompositeMapper.class, AdditionalMappingHelper.class })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = @Diagnostic(
            kind = Kind.ERROR,
            type = ErroneousSourceCompositeTargetCompositeMapper.class,
            line = 19,
            messageRegExp =
                "Ambiguous mapping methods found for mapping property "
                    + "\"org.mapstruct.ap.test.inheritance.complex.SourceExt prop1\" to .*Reference: "
                    + ".*Reference .*AdditionalMappingHelper\\.asReference\\(.*SourceBase source\\), "
                    + ".*Reference .*AdditionalMappingHelper\\.asReference\\(.*AdditionalFooSource source\\)"))
    public void ambiguousMappingMethodsReportError() {
    }

    private void assertResult(TargetComposite target) {
        assertThat( target ).isNotNull();
        assertThat( target.getProp1() ).isNotNull();
        assertThat( target.getProp1().getFoo() ).isEqualTo( 42 );

        assertThat( target.getProp2() ).isNotNull();
        assertThat( target.getProp2().getFoo() ).isEqualTo( 43 );

        assertThat( target.getProp3() ).isNotNull();
        assertThat( target.getProp3().getFoo() ).isEqualTo( 44 );
    }

    private SourceComposite createComposite() {
        SourceComposite comp = new SourceComposite();
        comp.setProp1( createSourceExt( 42 ) );
        comp.setProp2( createSourceExt2( 43 ) );
        comp.setProp3( createSourceBase( 44 ) );
        comp.setProp4( 999 );
        comp.setProp5( Arrays.asList( 42, 999 ) );
        return comp;
    }

    private SourceBase createSourceBase(int foo) {
        SourceBase s = new SourceBase();
        s.setFoo( foo );
        return s;
    }

    private SourceExt createSourceExt(int foo) {
        SourceExt s = new SourceExt();
        s.setFoo( foo );
        s.setBar( 47L );
        return s;
    }

    private SourceExt2 createSourceExt2(int foo) {
        SourceExt2 s = new SourceExt2();
        s.setFoo( foo );
        s.setBaz( 47L );
        return s;
    }
}
