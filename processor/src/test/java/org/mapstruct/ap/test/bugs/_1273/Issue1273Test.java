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
package org.mapstruct.ap.test.bugs._1273;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.factory.Mappers;

@IssueKey( "1273" )
@RunWith( AnnotationProcessorTestRunner.class )
@WithClasses( { EntityMapperReturnDefault.class, EntityMapperReturnNull.class, Dto.class, Entity.class } )
public class Issue1273Test {

    @Test
    public void shouldCorrectlyMapCollectionWithNullValueMappingStrategyReturnDefault() {
        EntityMapperReturnDefault entityMapper = Mappers.getMapper( EntityMapperReturnDefault.class );

        Entity entity = createEntityWithEmptyList();

        Dto dto = entityMapper.asTarget( entity );
        assertThat( dto.getLongs() ).isNotNull();
    }

    @Test
    public void shouldCorrectlyMapCollectionWithNullValueMappingStrategyReturnNull() {
        EntityMapperReturnNull entityMapper = Mappers.getMapper( EntityMapperReturnNull.class );

        Entity entity = createEntityWithEmptyList();

        Dto dto = entityMapper.asTarget( entity );
        assertThat( dto.getLongs() ).isNull();
    }

    private Entity createEntityWithEmptyList() {
        Entity entity = new Entity();
        entity.setLongs( null );

        return entity;
    }

}
