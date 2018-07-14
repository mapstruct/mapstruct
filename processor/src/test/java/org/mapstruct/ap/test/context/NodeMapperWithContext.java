/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.context;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ap.test.context.Node.Attribute;
import org.mapstruct.ap.test.context.NodeDto.AttributeDto;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@Mapper(uses = { CycleContextLifecycleMethods.class, FactoryContextMethods.class })
public interface NodeMapperWithContext {
    NodeMapperWithContext INSTANCE = Mappers.getMapper( NodeMapperWithContext.class );

    NodeDto nodeToNodeDto(@Context FactoryContext factoryContext, Node node, @Context CycleContext cycleContext);

    void nodeToNodeDto(@Context FactoryContext factoryContext, Node node, @MappingTarget NodeDto nodeDto,
            @Context CycleContext cycleContext);

    AttributeDto attributeToAttributeDto(Attribute attribute, @Context CycleContext cycleContext,
            @Context FactoryContext factoryContext);

    void attributeToAttributeDto(Attribute attribute, @MappingTarget AttributeDto nodeDto,
            @Context CycleContext cycleContext, @Context FactoryContext factoryContext);
}
