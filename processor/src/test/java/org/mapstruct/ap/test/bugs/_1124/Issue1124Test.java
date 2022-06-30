/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1124;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.test.bugs._1124.Issue1124Mapper.DTO;
import org.mapstruct.ap.test.bugs._1124.Issue1124Mapper.Entity;
import org.mapstruct.ap.test.bugs._1124.Issue1124Mapper.MappingContext;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@IssueKey("1124")
@WithClasses(Issue1124Mapper.class)
public class Issue1124Test {
    @ProcessorTest
    public void nestedPropertyWithContextCompiles() {
        Entity entity = new Entity();

        Entity subEntity = new Entity();
        subEntity.setId( 42L );
        entity.setEntity( subEntity );

        DTO dto = Mappers.getMapper( Issue1124Mapper.class ).map( entity, new MappingContext() );

        assertThat( dto.getId() ).isEqualTo( 42L );
    }
}
