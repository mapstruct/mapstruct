/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._931;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Verifies that source.nested == null, leads to target.id == null
 *
 * @author Sjaak Derksen
 */
@IssueKey( "931" )
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses( { Source.class, Nested.class, Target.class, SourceTargetMapper.class } )
public class Issue931Test {
    @Test
    public void shouldMapNestedNullToNull() {

        Source source = new Source();
        Target target = SourceTargetMapper.MAPPER.toTarget( source );

        assertThat( target.getId() ).isNull();
        assertThat( target.getIdStr() ).isNull();
    }
}
