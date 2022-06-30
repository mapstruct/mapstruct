/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.sourcewithextendsbound;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.imports.sourcewithextendsbound.astronautmapper.AstronautMapper;
import org.mapstruct.ap.test.imports.sourcewithextendsbound.dto.AstronautDto;
import org.mapstruct.ap.test.imports.sourcewithextendsbound.dto.SpaceshipDto;
import org.mapstruct.ap.test.imports.sourcewithextendsbound.entity.Astronaut;
import org.mapstruct.ap.test.imports.sourcewithextendsbound.entity.Spaceship;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
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
public class SourceTypeContainsCollectionWithExtendsBoundTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
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
