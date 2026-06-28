/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.util.Objects;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;

import static org.mapstruct.ap.internal.util.Collections.asSet;

/**
 * Conversion between {@code com.google.protobuf.ProtocolMessageEnum} and {@link Integer} types.
 * <p>
 * Protobuf enums have {@code getNumber()} method to convert to int and static {@code forNumber(int)}
 * method to convert from int.
 *
 * @author Freeman
 */
public class ProtobufEnumConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>.getNumber()";
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return String.format(
            "%s.requireNonNull(%s.forNumber( <SOURCE> ), \"Invalid enum number\")",
            conversionContext.getTypeFactory().getType( Objects.class ).createReferenceName(),
            conversionContext.getTargetType().createReferenceName()
        );
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return asSet(
            conversionContext.getTargetType(),
            conversionContext.getTypeFactory().getType( Objects.class )
        );
    }
}
