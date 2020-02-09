/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1148;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Entity.class,
    Issue1148Mapper.class
})
@IssueKey("1148")
public class Issue1148Test {

    @ProcessorTest
    public void shouldNotUseSameMethodForDifferentMappingsNestedSource() {
        Entity.Dto dto = new Entity.Dto();
        dto.nestedDto = new Entity.NestedDto( 30 );
        dto.nestedDto2 = new Entity.NestedDto( 40 );
        Entity entity = Issue1148Mapper.INSTANCE.toEntity( dto );

        assertThat( entity.getId() ).isEqualTo( 30 );
        assertThat( entity.getId2() ).isEqualTo( 40 );
    }

    @ProcessorTest
    public void shouldNotUseSameMethodForDifferentMappingsNestedTarget() {
        Entity.Dto dto = new Entity.Dto();
        dto.recipientId = 10;
        dto.senderId = 20;
        Entity entity = Issue1148Mapper.INSTANCE.toEntity( dto );

        assertThat( entity.getRecipient() ).isNotNull();
        assertThat( entity.getRecipient().nestedClient ).isNotNull();
        assertThat( entity.getRecipient().nestedClient.id ).isEqualTo( 10 );

        assertThat( entity.getSender() ).isNotNull();
        assertThat( entity.getSender().nestedClient ).isNotNull();
        assertThat( entity.getSender().nestedClient.id ).isEqualTo( 20 );
    }

    @ProcessorTest
    public void shouldNotUseSameMethodForDifferentMappingsSymmetric() {
        Entity.Dto dto = new Entity.Dto();
        dto.sameLevel = new Entity.ClientDto(new Entity.NestedDto( 30 ));
        dto.sameLevel2 = new Entity.ClientDto(new Entity.NestedDto( 40 ));
        Entity entity = Issue1148Mapper.INSTANCE.toEntity( dto );

        assertThat( entity.client ).isNotNull();
        assertThat( entity.client.nestedClient ).isNotNull();
        assertThat( entity.client.nestedClient.id ).isEqualTo( 30 );

        assertThat( entity.client2 ).isNotNull();
        assertThat( entity.client2.nestedClient ).isNotNull();
        assertThat( entity.client2.nestedClient.id ).isEqualTo( 40 );
    }

    @ProcessorTest
    public void shouldNotUseSameMethodForDifferentMappingsHalfSymmetric() {
        Entity.Dto dto = new Entity.Dto();
        dto.level = new Entity.ClientDto(new Entity.NestedDto( 80 ));
        dto.level2 = new Entity.ClientDto(new Entity.NestedDto( 90 ));
        Entity entity = Issue1148Mapper.INSTANCE.toEntity( dto );

        assertThat( entity.nested ).isNotNull();
        assertThat( entity.nested.id ).isEqualTo( 80 );

        assertThat( entity.nested2 ).isNotNull();
        assertThat( entity.nested2.id ).isEqualTo( 90 );
    }

    @ProcessorTest
    public void shouldNotUseSameMethodForDifferentMappingsNestedSourceMultiple() {
        Entity.Dto dto1 = new Entity.Dto();
        dto1.nestedDto = new Entity.NestedDto( 30 );
        Entity.Dto dto2 = new Entity.Dto();
        dto2.nestedDto2 = new Entity.NestedDto( 40 );
        Entity entity = Issue1148Mapper.INSTANCE.toEntity( dto1, dto2 );

        assertThat( entity.getId() ).isEqualTo( 30 );
        assertThat( entity.getId2() ).isEqualTo( 40 );
    }

    @ProcessorTest
    public void shouldNotUseSameMethodForDifferentMappingsNestedTargetMultiple() {
        Entity.Dto dto1 = new Entity.Dto();
        dto1.recipientId = 10;
        Entity.Dto dto2 = new Entity.Dto();
        dto2.senderId = 20;
        Entity entity = Issue1148Mapper.INSTANCE.toEntity( dto1, dto2 );

        assertThat( entity.getRecipient() ).isNotNull();
        assertThat( entity.getRecipient().nestedClient ).isNotNull();
        assertThat( entity.getRecipient().nestedClient.id ).isEqualTo( 10 );

        assertThat( entity.getSender() ).isNotNull();
        assertThat( entity.getSender().nestedClient ).isNotNull();
        assertThat( entity.getSender().nestedClient.id ).isEqualTo( 20 );
    }

    @ProcessorTest
    public void shouldNotUseSameMethodForDifferentMappingsSymmetricMultiple() {
        Entity.Dto dto1 = new Entity.Dto();
        dto1.sameLevel = new Entity.ClientDto(new Entity.NestedDto( 30 ));
        Entity.Dto dto2 = new Entity.Dto();
        dto2.sameLevel2 = new Entity.ClientDto(new Entity.NestedDto( 40 ));
        Entity entity = Issue1148Mapper.INSTANCE.toEntity( dto1, dto2 );

        assertThat( entity.client ).isNotNull();
        assertThat( entity.client.nestedClient ).isNotNull();
        assertThat( entity.client.nestedClient.id ).isEqualTo( 30 );

        assertThat( entity.client2 ).isNotNull();
        assertThat( entity.client2.nestedClient ).isNotNull();
        assertThat( entity.client2.nestedClient.id ).isEqualTo( 40 );
    }

    @ProcessorTest
    public void shouldNotUseSameMethodForDifferentMappingsHalfSymmetricMultiple() {
        Entity.Dto dto1 = new Entity.Dto();
        dto1.level = new Entity.ClientDto(new Entity.NestedDto( 80 ));
        Entity.Dto dto2 = new Entity.Dto();
        dto2.level2 = new Entity.ClientDto(new Entity.NestedDto( 90 ));
        Entity entity = Issue1148Mapper.INSTANCE.toEntity( dto1, dto2 );

        assertThat( entity.nested ).isNotNull();
        assertThat( entity.nested.id ).isEqualTo( 80 );

        assertThat( entity.nested2 ).isNotNull();
        assertThat( entity.nested2.id ).isEqualTo( 90 );
    }
}
