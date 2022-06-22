/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignore.inherit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

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
public class IgnorePropertyTest {

    @ProcessorTest
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

    @ProcessorTest
    @IssueKey("1933")
    public void shouldInheritIgnoreByDefaultFromBase() {

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

    @ProcessorTest
    @IssueKey("1933")
    public void shouldOnlyIgnoreBase() {

        WorkBenchDto workBenchDto = new WorkBenchDto();
        workBenchDto.setArticleName( "MyBench" );
        workBenchDto.setArticleDescription( "Beautiful" );
        workBenchDto.setCreationDate( new Date() );
        workBenchDto.setModificationDate( new Date() );

        WorkBenchEntity benchTarget = ToolMapper.INSTANCE.mapBenchWithImplicit( workBenchDto );

        assertThat( benchTarget ).isNotNull();
        assertThat( benchTarget.getArticleName() ).isEqualTo( "MyBench" );
        assertThat( benchTarget.getDescription() ).isEqualTo( "Beautiful" );
        assertThat( benchTarget.getKey() ).isNull();
        assertThat( benchTarget.getModificationDate() ).isNull();
        assertThat( benchTarget.getCreationDate() ).isNull();
    }

}
