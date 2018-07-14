/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignore.expand;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for ignoring properties during the mapping.
 *
 * @author Sjaak Derksen
 */
@WithClasses({
    ExpandedToolbox.class,
    Hammer.class,
    Wrench.class,
    FlattenedToolBox.class,
    ToolBoxMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class IgnorePropertyTest {

    @Test
    @IssueKey("1392")
    public void shouldIgnoreAll() {
        FlattenedToolBox toolboxSource = new FlattenedToolBox();
        toolboxSource.setBrand( "Stanley" );
        toolboxSource.setHammerDescription( "heavy" );
        toolboxSource.setHammerSize( 5 );
        toolboxSource.setWrenchIsBahco( Boolean.TRUE );
        toolboxSource.setWrenchDescription( "generic use" );

        ExpandedToolbox toolboxTarget = ToolBoxMapper.INSTANCE.expand( toolboxSource );

        assertThat( toolboxTarget ).isNotNull();
        assertThat( toolboxTarget.getBrand() ).isNull();
        assertThat( toolboxTarget.getHammer() ).isNotNull();
        assertThat( toolboxTarget.getHammer().getDescription() ).isEqualTo( "heavy" );
        assertThat( toolboxTarget.getHammer().getSize() ).isNull();
        assertThat( toolboxTarget.getWrench() ).isNull();
    }

}
