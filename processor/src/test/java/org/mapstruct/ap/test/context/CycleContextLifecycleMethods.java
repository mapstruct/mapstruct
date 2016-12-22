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

import org.mapstruct.BeforeMapping;
import org.mapstruct.Context;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.mapstruct.ap.test.context.Node.Attribute;
import org.mapstruct.ap.test.context.NodeDto.AttributeDto;

/**
 * @author Andreas Gudian
 */
public class CycleContextLifecycleMethods {

    public NodeDto createNodeDTO(@Context FactoryContext context) {
        return context.createNode();
    }

    @ObjectFactory
    public AttributeDto createAttributeDTO(Attribute source, @Context FactoryContext context) {
        return context.createAttributeDTO( source );
    }

    @BeforeMapping
    public <T> T getInstance(Object source, @TargetType Class<T> type, @Context CycleContext cycleContext) {
        return cycleContext.getMappedInstance( source, type );
    }

    @BeforeMapping
    public void setInstance(Object source, @MappingTarget Object target, @Context CycleContext cycleContext) {
        cycleContext.storeMappedInstance( source, target );
    }
}
