/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.protobuf;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.protobuf.MessageOrBuilder;
import org.mapstruct.ap.test.protobuf._target.UserDto;
import org.mapstruct.ap.test.protobuf._target.UserProtoBuilder;
import org.mapstruct.ap.test.protobuf.source.ItemDto;
import org.mapstruct.ap.test.protobuf.source.UserProto;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for protobuf support.
 * Verifies that ProtobufAccessorNamingStrategy correctly handles:
 * - Repeated fields with getXxxList() pattern
 * - Map fields with getXxxMap() pattern
 * - Adder methods addXxx() for repeated fields
 * - Putter methods putXxx() for map fields
 *
 * @author MapStruct Authors
 */
@WithClasses({
    MessageOrBuilder.class,
    UserProto.class,
    UserDto.class,
    UserProtoBuilder.class,
    ItemDto.class,
    ProtobufMapper.class
})
public class ProtobufTest {

    @ProcessorTest
    public void testProtobufRepeatedFieldToDto() {
        // Given: Protobuf message with repeated field
        UserProto proto = new UserProto();
        proto.setItemsList(Arrays.asList("item1", "item2", "item3"));

        // When: Mapping to DTO
        UserDto dto = ProtobufMapper.INSTANCE.protoToDto(proto);

        // Then: Items should be mapped using addItem() adder method
        assertThat(dto).isNotNull();
        assertThat(dto.getItems()).isNotNull();
        assertThat(dto.getItems()).containsExactly("item1", "item2", "item3");
    }

    @ProcessorTest
    public void testProtobufMapFieldToDto() {
        // Given: Protobuf message with map field
        UserProto proto = new UserProto();
        Map<String, Integer> attributes = new HashMap<>();
        attributes.put("age", 30);
        attributes.put("score", 100);
        proto.setAttributesMap(attributes);

        // When: Mapping to DTO
        UserDto dto = ProtobufMapper.INSTANCE.protoToDto(proto);

        // Then: Attributes should be mapped using putAttribute() putter method
        assertThat(dto).isNotNull();
        assertThat(dto.getAttributes()).isNotNull();
        assertThat(dto.getAttributes()).hasSize(2);
        assertThat(dto.getAttributes()).containsEntry("age", 30);
        assertThat(dto.getAttributes()).containsEntry("score", 100);
    }

    @ProcessorTest
    public void testProtobufBothFieldsToDto() {
        // Given: Protobuf message with both repeated and map fields
        UserProto proto = new UserProto();
        proto.setItemsList(Arrays.asList("a", "b"));

        Map<String, Integer> attributes = new HashMap<>();
        attributes.put("key1", 1);
        attributes.put("key2", 2);
        proto.setAttributesMap(attributes);

        // When: Mapping to DTO
        UserDto dto = ProtobufMapper.INSTANCE.protoToDto(proto);

        // Then: Both fields should be properly mapped
        assertThat(dto).isNotNull();
        assertThat(dto.getItems()).containsExactly("a", "b");
        assertThat(dto.getAttributes()).containsEntry("key1", 1);
        assertThat(dto.getAttributes()).containsEntry("key2", 2);
    }

    @ProcessorTest
    public void testDtoToProtobufBuilder() {
        // Given: DTO with items and attributes
        ItemDto dto = new ItemDto();
        dto.setItems(Arrays.asList("x", "y", "z"));

        Map<String, Integer> attributes = new HashMap<>();
        attributes.put("prop1", 10);
        attributes.put("prop2", 20);
        dto.setAttributes(attributes);

        // When: Mapping to protobuf builder
        UserProtoBuilder builder = ProtobufMapper.INSTANCE.dtoToProtoBuilder(dto);

        // Then: Builder should use addItems() and putAttributes() methods
        assertThat(builder).isNotNull();
        assertThat(builder.getItemsList()).containsExactly("x", "y", "z");
        assertThat(builder.getAttributesMap()).containsEntry("prop1", 10);
        assertThat(builder.getAttributesMap()).containsEntry("prop2", 20);
    }

    @ProcessorTest
    public void testEmptyProtobufMessage() {
        // Given: Empty protobuf message
        UserProto proto = new UserProto();

        // When: Mapping to DTO
        UserDto dto = ProtobufMapper.INSTANCE.protoToDto(proto);

        // Then: DTO should be created (fields may be null)
        assertThat(dto).isNotNull();
    }

    @ProcessorTest
    public void testEmptyDto() {
        // Given: Empty DTO
        ItemDto dto = new ItemDto();

        // When: Mapping to protobuf builder
        UserProtoBuilder builder = ProtobufMapper.INSTANCE.dtoToProtoBuilder(dto);

        // Then: Builder should be created (collections may be empty)
        assertThat(builder).isNotNull();
    }
}
