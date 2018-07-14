/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks.returning;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author Pascal Grün
 */
@Mapper
public abstract class NodeMapperDefault {
    public static final NodeMapperDefault INSTANCE = Mappers.getMapper( NodeMapperDefault.class );

    public abstract NodeDto nodeToNodeDto(Node node);

    public abstract void nodeToNodeDto(Node node, @MappingTarget NodeDto nodeDto);

    protected abstract AttributeDto attributeToAttributeDto(Attribute attribute);

    protected abstract void attributeToAttributeDto(Attribute attribute, @MappingTarget AttributeDto nodeDto);
}
