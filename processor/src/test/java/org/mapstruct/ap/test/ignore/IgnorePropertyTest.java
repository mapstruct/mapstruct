/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.ignore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Test for ignoring properties during the mapping.
 *
 * @author Gunnar Morling
 */
@WithClasses({ Animal.class, AnimalDto.class, AnimalMapper.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class IgnorePropertyTest {

    @Test
    @IssueKey("72")
    public void shouldNotPropagateIgnoredPropertyGivenViaSourceAttribute() {
        Animal animal = new Animal( "Bruno", 100, 23 );

        AnimalDto animalDto = AnimalMapper.INSTANCE.animalToDto( animal );

        assertThat( animalDto ).isNotNull();
        assertThat( animalDto.getName() ).isEqualTo( "Bruno" );
        assertThat( animalDto.getSize() ).isNull();
    }

    @Test
    @IssueKey("72")
    public void shouldNotPropagateIgnoredPropertyGivenViaTargetAttribute() {
        Animal animal = new Animal( "Bruno", 100, 23 );

        AnimalDto animalDto = AnimalMapper.INSTANCE.animalToDto( animal );

        assertThat( animalDto ).isNotNull();
        assertThat( animalDto.getAge() ).isNull();
    }

    @Test
    @IssueKey("72")
    public void shouldNotPropagateIgnoredPropertyInReverseMapping() {
        AnimalDto animalDto = new AnimalDto( "Bruno", 100, 23 );

        Animal animal = AnimalMapper.INSTANCE.animalDtoToAnimal( animalDto );

        assertThat( animal ).isNotNull();
        assertThat( animal.getAge() ).isNull();
    }
}
