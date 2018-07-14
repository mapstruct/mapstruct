/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.context;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@Mapper(uses = FactoryContextMethods.class)
public interface AutomappingNodeMapperWithSelfContainingContext {

    AutomappingNodeMapperWithSelfContainingContext INSTANCE =
        Mappers.getMapper( AutomappingNodeMapperWithSelfContainingContext.class );

    NodeDto nodeToNodeDto(Node node, @Context SelfContainingCycleContext cycleContext,
            @Context FactoryContext factoryContext);

    void nodeToNodeDto(Node node, @MappingTarget NodeDto nodeDto, @Context SelfContainingCycleContext cycleContext,
            @Context FactoryContext factoryContext);
}
