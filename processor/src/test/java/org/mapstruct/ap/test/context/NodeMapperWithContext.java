/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
@Mapper(uses = CycleContextLifecycleMethods.class)
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
