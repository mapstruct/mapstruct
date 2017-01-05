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
package org.mapstruct.ap.test.imports.sourcewithextendsbound;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.imports.sourcewithextendsbound.astronautmapper.AstronautMapper;
import org.mapstruct.ap.test.imports.sourcewithextendsbound.dto.AstronautDto;
import org.mapstruct.ap.test.imports.sourcewithextendsbound.dto.SpaceshipDto;
import org.mapstruct.ap.test.imports.sourcewithextendsbound.entity.Astronaut;
import org.mapstruct.ap.test.imports.sourcewithextendsbound.entity.Spaceship;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * Test for generating a mapper which references nested types (static inner classes).
 *
 * @author Gunnar Morling
 */
@WithClasses({
    Astronaut.class, Spaceship.class, AstronautDto.class, SpaceshipDto.class, SpaceshipMapper.class,
    AstronautMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class SourceTypeContainsCollectionWithExtendsBoundTest {

    private final GeneratedSource generatedSource = new GeneratedSource();

    @Rule
    public GeneratedSource getGeneratedSource() {
        return generatedSource;
    }

    @Test
    @IssueKey("768")
    public void generatesImportsForCollectionWithExtendsBoundInSourceType() {
        Astronaut astronaut = new Astronaut();
        astronaut.setName( "Bob" );

        Spaceship spaceship = new Spaceship();
        spaceship.setAstronauts( Collections.singleton( astronaut ) );

        SpaceshipDto spaceshipDto = SpaceshipMapper.INSTANCE.spaceshipToDto( spaceship );

        assertThat( spaceshipDto ).isNotNull();
        assertThat( spaceshipDto.getAstronauts() ).extracting( "name" ).containsOnly( "Bob" );

        generatedSource.forMapper( SpaceshipMapper.class ).containsImportFor( Astronaut.class );
        generatedSource.forMapper( SpaceshipMapper.class ).containsImportFor( Spaceship.class );
        generatedSource.forMapper( SpaceshipMapper.class ).containsImportFor( AstronautDto.class );
        generatedSource.forMapper( SpaceshipMapper.class ).containsImportFor( SpaceshipDto.class );
    }
}
