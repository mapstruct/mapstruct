/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.context.erroneous;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.context.CycleContext;
import org.mapstruct.ap.test.context.Node;
import org.mapstruct.ap.test.context.NodeDto;

/**
 * @author Andreas Gudian
 */
@Mapper
public interface ErroneousNodeMapperWithNonUniqueContextTypes {

    NodeDto nodeToNodeDto(Node node, @Context CycleContext cycleContext, @Context CycleContext otherCycleContext);
}
