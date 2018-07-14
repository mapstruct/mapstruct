/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._516;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Reproducer for https://github.com/mapstruct/mapstruct/issues/516.
 *
 * @author Sjaak Derksen
 */
@IssueKey( "516" )
@RunWith(AnnotationProcessorTestRunner.class)
public class Issue516Test {

    @Test
    @WithClasses( { SourceTargetMapper.class, Source.class, Target.class } )
    public void shouldAddNullPtrCheckAroundSourceForAdder() {

        Source source = new Source();

        Target target = SourceTargetMapper.STM.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getElements() ).isNull();

    }
}
