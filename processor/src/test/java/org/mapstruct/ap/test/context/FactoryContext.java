/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.context;

import org.mapstruct.Context;
import org.mapstruct.ap.test.context.Node.Attribute;
import org.mapstruct.ap.test.context.NodeDto.AttributeDto;

/**
 * A type to be used as {@link Context} parameter to create NodeDto and AttributeDto instances
 *
 * @author Andreas Gudian
 */
public class FactoryContext {
    private int nodeCounter;
    private int attributeMagicNumberOffset;

    public FactoryContext(int initialCounter, int attributeMagicNumberOffset) {
        this.nodeCounter = initialCounter;
        this.attributeMagicNumberOffset = attributeMagicNumberOffset;
    }

    public NodeDto createNode() {
        return new NodeDto( nodeCounter++ );
    }

    public AttributeDto createAttributeDto(Attribute source) {
        return new AttributeDto( source.getMagicNumber() + attributeMagicNumberOffset );
    }
}
