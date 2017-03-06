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
package org.mapstruct.ap.test.bugs._1124;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.bugs._1124.Issue1124Mapper.DTO;
import org.mapstruct.ap.test.bugs._1124.Issue1124Mapper.Entity;
import org.mapstruct.ap.test.bugs._1124.Issue1124Mapper.MappingContext;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses(Issue1124Mapper.class)
public class Issue1124Test {
    @Test
    public void nestedPropertyWithContextCompiles() {
        Entity entity = new Entity();

        Entity subEntity = new Entity();
        subEntity.setId( 42L );
        entity.setEntity( subEntity );

        DTO dto = Mappers.getMapper( Issue1124Mapper.class ).map( entity, new MappingContext() );

        assertThat( dto.getId() ).isEqualTo( 42L );
    }
}
