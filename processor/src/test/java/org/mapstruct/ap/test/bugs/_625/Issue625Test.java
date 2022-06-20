/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._625;

import org.mapstruct.factory.Mappers;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Andreas Gudian
 *
 */
@WithClasses({
    ObjectFactory.class,
    Source.class,
    Target.class,
    SourceTargetMapper.class
})
@IssueKey("625")
public class Issue625Test {
    @ProcessorTest
    public void ignoresFactoryMethods() {
        Source s = new Source();
        s.setProp( "works" );

        Target target = new Target( true );
        Mappers.getMapper( SourceTargetMapper.class ).intoTarget( s, target );

        assertThat( target.getProp() ).isEqualTo( "works" );
    }
}
