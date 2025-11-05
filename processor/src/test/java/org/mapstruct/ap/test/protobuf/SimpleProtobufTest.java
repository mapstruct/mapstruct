/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.protobuf;

import java.util.Arrays;

import com.google.protobuf.MessageOrBuilder;
import org.mapstruct.ap.test.protobuf._target.SimpleUserDto;
import org.mapstruct.ap.test.protobuf.source.SimpleUserProto;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Simple test for protobuf repeated field support.
 */
@WithClasses({
    MessageOrBuilder.class,
    SimpleUserProto.class,
    SimpleUserDto.class,
    SimpleProtobufMapper.class
})
public class SimpleProtobufTest {

    @ProcessorTest
    public void testProtobufRepeatedFieldToDto() {
        // Given: Protobuf message with repeated field
        SimpleUserProto proto = new SimpleUserProto();
        proto.setItemsList(Arrays.asList("item1", "item2", "item3"));

        // When: Mapping to DTO
        SimpleUserDto dto = SimpleProtobufMapper.INSTANCE.protoToDto(proto);

        // Then: Items should be mapped using addItem() adder method
        assertThat(dto).isNotNull();
        assertThat(dto.getItems()).isNotNull();
        assertThat(dto.getItems()).containsExactly("item1", "item2", "item3");
    }
}
