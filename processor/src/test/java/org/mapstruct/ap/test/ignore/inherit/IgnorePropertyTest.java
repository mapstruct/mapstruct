/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignore.inherit;

import java.util.Date;

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
    HammerDto.class,
    HammerEntity.class,
    ToolEntity.class,
    BaseEntity.class,
    ToolDto.class,
    WorkBenchDto.class,
    WorkBenchEntity.class,
    ToolMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class IgnorePropertyTest {

    @Test
    @IssueKey("1392")
    /**
     * Should not issue warnings on unmapped target properties
     */
    public void shouldIgnoreAllExeptOveriddenInherited() {
        HammerDto hammer = new HammerDto();
        hammer.setToolType( "smash" );
        hammer.setToolSize( 5 );

        HammerEntity toolTarget = ToolMapper.INSTANCE.mapHammer( hammer );

        assertThat( toolTarget ).isNotNull();
        assertThat( toolTarget.getSize() ).isNull();
        assertThat( toolTarget.getType() ).isEqualTo( "smash" );
        assertThat( toolTarget.getKey() ).isNull();
        assertThat( toolTarget.getCreationDate() ).isNull();
        assertThat( toolTarget.getModificationDate() ).isNull();

    }

    @Test
    @IssueKey("1933")
    public void shouldIgnoreBase() {

        WorkBenchDto workBenchDto = new WorkBenchDto();
        workBenchDto.setArticleName( "MyBench" );
        workBenchDto.setArticleDescription( "Beautiful" );
        workBenchDto.setCreationDate( new Date() );
        workBenchDto.setModificationDate( new Date() );

        WorkBenchEntity benchTarget = ToolMapper.INSTANCE.mapBench( workBenchDto );

        assertThat( benchTarget ).isNotNull();
        assertThat( benchTarget.getArticleName() ).isNull();
        assertThat( benchTarget.getDescription() ).isEqualTo( "Beautiful" );
        assertThat( benchTarget.getKey() ).isNull();
        assertThat( benchTarget.getModificationDate() ).isNull();
        assertThat( benchTarget.getCreationDate() ).isNull();
    }

}
