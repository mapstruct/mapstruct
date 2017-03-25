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
package org.mapstruct.ap.test.bugs._1148;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Entity.class,
    Issue1148Mapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1148")
public class Issue1148Test {

    @Test
    public void shouldNotUseSameMethodForDifferentMappingsNestedSource() throws Exception {
        Entity.Dto dto = new Entity.Dto();
        dto.nestedDto = new Entity.NestedDto( 30 );
        dto.nestedDto2 = new Entity.NestedDto( 40 );
        Entity entity = Issue1148Mapper.INSTANCE.toEntity( dto );

        assertThat( entity.getId() ).isEqualTo( 30 );
        assertThat( entity.getId2() ).isEqualTo( 40 );
    }

    @Test
    public void shouldNotUseSameMethodForDifferentMappingsNestedTarget() throws Exception {
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

    @Test
    public void shouldNotUseSameMethodForDifferentMappingsSymmetric() throws Exception {
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

    @Test
    public void shouldNotUseSameMethodForDifferentMappingsHalfSymmetric() throws Exception {
        Entity.Dto dto = new Entity.Dto();
        dto.level = new Entity.ClientDto(new Entity.NestedDto( 80 ));
        dto.level2 = new Entity.ClientDto(new Entity.NestedDto( 90 ));
        Entity entity = Issue1148Mapper.INSTANCE.toEntity( dto );

        assertThat( entity.nested ).isNotNull();
        assertThat( entity.nested.id ).isEqualTo( 80 );

        assertThat( entity.nested2 ).isNotNull();
        assertThat( entity.nested2.id ).isEqualTo( 90 );
    }

    @Test
    public void shouldNotUseSameMethodForDifferentMappingsNestedSourceMultiple() throws Exception {
        Entity.Dto dto1 = new Entity.Dto();
        dto1.nestedDto = new Entity.NestedDto( 30 );
        Entity.Dto dto2 = new Entity.Dto();
        dto2.nestedDto2 = new Entity.NestedDto( 40 );
        Entity entity = Issue1148Mapper.INSTANCE.toEntity( dto1, dto2 );

        assertThat( entity.getId() ).isEqualTo( 30 );
        assertThat( entity.getId2() ).isEqualTo( 40 );
    }

    @Test
    public void shouldNotUseSameMethodForDifferentMappingsNestedTargetMultiple() throws Exception {
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

    @Test
    public void shouldNotUseSameMethodForDifferentMappingsSymmetricMultiple() throws Exception {
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

    @Test
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
