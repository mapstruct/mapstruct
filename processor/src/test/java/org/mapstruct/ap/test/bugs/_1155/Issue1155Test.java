/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1155;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Entity.class,
    Issue1155Mapper.class
})
@IssueKey("1155")
public class Issue1155Test {

    @ProcessorTest
    public void shouldCompile() {

        Entity.Dto dto = new Entity.Dto();
        dto.clientId = 10;
        Entity entity = Issue1155Mapper.INSTANCE.toEntity( dto );

        assertThat( entity.client ).isNotNull();
        assertThat( entity.client.id ).isEqualTo( 10 );
    }
}
