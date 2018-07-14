/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.oneway;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for propagation of attribute without setter in source and getter in
 * target.
 *
 * @author Gunnar Morling
 */
@WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class OnewayTest {

    @Test
    @IssueKey("17")
    public void shouldMapAttributeWithoutSetterInSourceType() {
        Source source = new Source();

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.retrieveFoo() ).isEqualTo( Long.valueOf( 42 ) );
    }

    @Test
    @IssueKey("41")
    public void shouldReverseMapAttributeWithoutSetterInTargetType() {
        Target target = new Target();

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.retrieveBar() ).isEqualTo( 23 );
    }

    @Test
    @IssueKey("104")
    public void shouldMapMappedAttributeWithoutSetterInSourceType() {
        Source source = new Source();

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getQux() ).isEqualTo( 23L );
    }
}
