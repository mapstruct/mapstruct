/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.ProtobufConstants;

/**
 * Conversion between {@code com.google.protobuf.Int32Value} and {@link Integer}.
 *
 * @author Freeman
 */
public class ProtobufInt32ValueConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>.getValue()";
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return conversionContext.getTargetType().createReferenceName() + ".of( <SOURCE> )";
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(
            conversionContext.getTypeFactory().getType( ProtobufConstants.INT32_VALUE_FQN )
        );
    }
}
