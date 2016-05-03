/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.statefullmappers;


import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@IssueKey( "751" )
@WithClasses({
    UniversityDto.class,
    UniversityEntity.class,
    ClassNumberDto.class,
    ClassNameEntity.class,
    State.class,
    StatefullMapper.class,
    MapperReferencingStatefullMapper.class,
    FactoryOfMappers.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class MapperFactoryTest {

    @Test
    @IssueKey( "751" )
    public void shouldGenerateMapperWithOverloadedConstructor() {

        FactoryOfMappers mf = Mappers.getMapperFactory( FactoryOfMappers.class );

        // create a state object, hey.. its a real state :).
        StatefullMapper statefullMapperNY = mf.createMapperStatefullMapper( new State( "New York" ) );
        MapperReferencingStatefullMapper otherMapperNY = mf.createMapperReferencingStatefullMapper( statefullMapperNY );

        // create a DTO
        UniversityDto universityDto = new UniversityDto();
        ClassNumberDto classNumberDto = new ClassNumberDto();
        classNumberDto.setNumber( 1 );
        universityDto.setClassNumber( classNumberDto );
        UniversityEntity universtityEntityCU = otherMapperNY.toUniversityEntity( universityDto );

        // expect
        assertThat( universtityEntityCU ).isNotNull();
        assertThat( universtityEntityCU.getClassNameEntity() ).isNotNull();
        assertThat( universtityEntityCU.getClassNameEntity().getName() ).isEqualTo( "English" );

        // create new mappers
        StatefullMapper statefullMapperCAL = mf.createMapperStatefullMapper( new State( "California" ) );

        MapperReferencingStatefullMapper otherMapperCAL =
            mf.createMapperReferencingStatefullMapper( statefullMapperCAL );

        // create a DTO
        UniversityEntity universtityEntityUCLA = otherMapperCAL.toUniversityEntity( universityDto );

        // expect
        assertThat( universtityEntityUCLA ).isNotNull();
        assertThat( universtityEntityUCLA.getClassNameEntity() ).isNotNull();
        assertThat( universtityEntityUCLA.getClassNameEntity().getName() ).isEqualTo( "Math" );

    }

}
