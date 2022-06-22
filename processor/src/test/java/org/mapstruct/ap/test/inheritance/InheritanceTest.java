/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritance;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * Test for propagation of attributes inherited from super types.
 *
 * @author Gunnar Morling
 */
@WithClasses({ SourceBase.class, SourceExt.class, TargetBase.class, TargetExt.class, SourceTargetMapper.class })
public class InheritanceTest {

    @ProcessorTest
    @IssueKey("17")
    public void shouldMapAttributeFromSuperType() {
        SourceExt source = createSource();

        TargetExt target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertResult( target );
    }

    @ProcessorTest
    @IssueKey("19")
    public void shouldMapAttributeFromSuperTypeUsingTargetParameter() {
        SourceExt source = createSource();

        TargetExt target = new TargetExt();
        SourceTargetMapper.INSTANCE.sourceToTargetWithTargetParameter( target, source );

        assertResult( target );
    }

    @ProcessorTest
    @IssueKey("19")
    public void shouldMapAttributeFromSuperTypeUsingReturnedTargetParameter() {
        SourceExt source = createSource();

        TargetExt target = new TargetExt();
        TargetBase result = SourceTargetMapper.INSTANCE.sourceToTargetWithTargetParameterAndReturn( source, target );

        assertThat( target ).isSameAs( result );

        assertResult( target );
    }

    @ProcessorTest
    @IssueKey("1752")
    public void shouldMapAttributeFromSuperTypeUsingReturnedTargetParameterAndNullSource() {

        TargetExt target = new TargetExt();
        target.setFoo( 42L );
        target.publicFoo = 52L;
        target.setBar( 23 );
        TargetBase result = SourceTargetMapper.INSTANCE.sourceToTargetWithTargetParameterAndReturn( null, target );

        assertThat( target ).isSameAs( result );
        assertResult( target );
    }

    private void assertResult(TargetExt target) {
        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( Long.valueOf( 42 ) );
        assertThat( target.publicFoo ).isEqualTo( Long.valueOf( 52 ) );
        assertThat( target.getBar() ).isEqualTo( 23 );
    }

    private SourceExt createSource() {
        SourceExt source = new SourceExt();
        source.setFoo( 42 );
        source.publicFoo = 52;
        source.setBar( 23L );
        return source;
    }

    @ProcessorTest
    @IssueKey("17")
    public void shouldReverseMapAttributeFromSuperType() {
        TargetExt target = new TargetExt();
        target.setFoo( 42L );
        target.publicFoo = 52L;
        target.setBar( 23 );

        SourceExt source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getFoo() ).isEqualTo( 42 );
        assertThat( source.publicFoo ).isEqualTo( 52 );
        assertThat( source.getBar() ).isEqualTo( Long.valueOf( 23 ) );
    }
}
