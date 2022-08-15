/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Andreas Gudian
 *
 */
public class GenericsTest {

    @ProcessorTest
    @IssueKey("574")
    @WithClasses({
        AbstractTo.class,
        AbstractIdHoldingTo.class,
        Source.class,
        SourceTargetMapper.class,
        TargetTo.class
    })
    public void mapsIdCorrectly() {
        TargetTo target = new TargetTo();
        target.setId( 41L );
        assertThat( SourceTargetMapper.INSTANCE.toSource( target ).getId() ).isEqualTo( 41L );
    }

    @ProcessorTest
    @IssueKey("2954")
    @WithClasses({
        GenericTargetContainer.class,
        GenericSourceContainer.class,
        GenericMapper.class,
    })
    public void mapsSameTypeCorrectly() {
        String containedObject = "Test";
        GenericSourceContainer<String> container = new GenericSourceContainer<>(containedObject);

        assertThat( GenericMapper.INSTANCE.map( container ).getContained() ).isSameAs( containedObject );
    }
}
