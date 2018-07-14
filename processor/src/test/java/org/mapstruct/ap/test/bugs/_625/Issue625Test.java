/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._625;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 *
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    ObjectFactory.class,
    Source.class,
    Target.class,
    SourceTargetMapper.class
})
@IssueKey("625")
public class Issue625Test {
    @Test
    public void ignoresFactoryMethods() {
        Source s = new Source();
        s.setProp( "works" );

        Target target = new Target( true );
        Mappers.getMapper( SourceTargetMapper.class ).intoTarget( s, target );

        assertThat( target.getProp() ).isEqualTo( "works" );
    }
}
