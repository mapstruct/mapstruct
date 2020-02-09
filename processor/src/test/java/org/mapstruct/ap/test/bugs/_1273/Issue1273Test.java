/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1273;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@IssueKey( "1273" )
@WithClasses( { EntityMapperReturnDefault.class, EntityMapperReturnNull.class, Dto.class, Entity.class } )
public class Issue1273Test {

    @ProcessorTest
    public void shouldCorrectlyMapCollectionWithNullValueMappingStrategyReturnDefault() {
        EntityMapperReturnDefault entityMapper = Mappers.getMapper( EntityMapperReturnDefault.class );

        Entity entity = createEntityWithEmptyList();

        Dto dto = entityMapper.asTarget( entity );
        assertThat( dto.getLongs() ).isNotNull();
    }

    @ProcessorTest
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
