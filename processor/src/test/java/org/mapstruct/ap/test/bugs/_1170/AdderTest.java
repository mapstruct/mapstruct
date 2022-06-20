/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1170;

import java.util.Arrays;

import org.assertj.core.api.ListAssert;
import org.mapstruct.ap.test.bugs._1170._target.Target;
import org.mapstruct.ap.test.bugs._1170.source.Source;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Cornelius Dirmeier
 */
@WithClasses({
    Source.class,
    Target.class,
    AdderSourceTargetMapper.class,
    PetMapper.class
})
public class AdderTest {

    @IssueKey("1170")
    @ProcessorTest
    public void testWildcardAdder() {
        Source source = new Source();
        source.addWithoutWildcard( "mouse" );
        source.addWildcardInTarget( "mouse" );
        source.addWildcardInSource( "mouse" );
        source.addWildcardInBoth( "mouse" );
        source.addWildcardAdderToSetter( "mouse" );

        Target target = AdderSourceTargetMapper.INSTANCE.toTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getWithoutWildcards() ).containsExactly( 2L );
        assertThat( target.getWildcardInSources() ).containsExactly( 2L );
        ( (ListAssert<Long>) assertThat( target.getWildcardInTargets() ) ).containsExactly( 2L );
        ( (ListAssert<Long>) assertThat( target.getWildcardInBoths() ) ).containsExactly( 2L );
        assertThat( target.getWildcardAdderToSetters() ).containsExactly( 2L );
    }

    @IssueKey("1170")
    @ProcessorTest
    public void testWildcardAdderTargetToSource() {
        Target target = new Target();
        target.addWithoutWildcard( 2L );
        target.addWildcardInTarget( 2L );
        target.getWildcardInSources().add( 2L );
        target.addWildcardInBoth( 2L );
        target.setWildcardAdderToSetters( Arrays.asList( 2L ) );

        Source source = AdderSourceTargetMapper.INSTANCE.toSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getWithoutWildcards() ).containsExactly( "mouse" );
        ( (ListAssert<String>) assertThat( source.getWildcardInSources() ) ).containsExactly( "mouse" );
        assertThat( source.getWildcardInTargets() ).containsExactly( "mouse" );
        ( (ListAssert<String>) assertThat( source.getWildcardInBoths() ) ).containsExactly( "mouse" );
        ( (ListAssert<String>) assertThat( source.getWildcardAdderToSetters() ) ).containsExactly( "mouse" );
    }
}
