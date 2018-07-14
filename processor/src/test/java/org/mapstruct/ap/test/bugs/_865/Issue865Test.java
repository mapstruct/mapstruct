/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._865;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 *
 * @author Sjaak Derksen
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses( {
    ProjectDto.class,
    ProjectEntity.class,
    ProjectEntityWithoutSetter.class,
    ProjCoreUserEntity.class,
    ProjMemberDto.class,
    ProjectMapper.class
} )
public class Issue865Test {

    @Test
    public void shouldGenerateNpeCheckBeforCallingAddAllWhenInUpdateMethods() {

        ProjectDto dto = new ProjectDto();
        dto.setName( "myProject" );

        ProjectEntity entity = new ProjectEntity();
        entity.setCoreUsers( null );

        ProjectMapper.INSTANCE.mapProjectUpdate( dto, entity );

        assertThat( entity.getName() ).isEqualTo( "myProject" );
        assertThat( entity.getCoreUsers() ).isNull();
    }

    public void shouldGenerateNpeCheckBeforCallingAddAllWhenInUpdateMethodsAndTargetWithoutSetter() {

        ProjectDto dto = new ProjectDto();
        dto.setName( "myProject" );

        ProjectEntityWithoutSetter entity = new ProjectEntityWithoutSetter();

        ProjectMapper.INSTANCE.mapProjectUpdateWithoutGetter( dto, entity );

        assertThat( entity.getName() ).isEqualTo( "myProject" );
        assertThat( entity.getCoreUsers() ).isNull();
    }
}
