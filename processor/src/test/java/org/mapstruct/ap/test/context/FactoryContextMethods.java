/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.context;

import org.mapstruct.Context;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ap.test.context.Node.Attribute;
import org.mapstruct.ap.test.context.NodeDto.AttributeDto;

/**
 * @author Andreas Gudian
 */
public class FactoryContextMethods {

    public NodeDto createNodeDto(@Context FactoryContext context) {
        return context.createNode();
    }

    @ObjectFactory
    public AttributeDto createAttributeDto(Attribute source, @Context FactoryContext context) {
        return context.createAttributeDto( source );
    }
}
