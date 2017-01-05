/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
