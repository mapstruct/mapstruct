/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.util.Collections;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Strings;

import static org.mapstruct.ap.internal.conversion.ReverseConversion.inverse;

/**
 * @author Filip Hrisafov
 */
public class TypeToOptionalConversion extends SimpleConversion {

    static final TypeToOptionalConversion TYPE_TO_OPTIONAL_CONVERSION = new TypeToOptionalConversion();
    static final ConversionProvider OPTIONAL_TO_TYPE_CONVERSION = inverse( TYPE_TO_OPTIONAL_CONVERSION );

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return conversionContext.getTargetType().asRawType().createReferenceName() + ".of( <SOURCE> )";
    }

    @Override
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        return Collections.singleton( conversionContext.getTargetType().asRawType() );
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        StringBuilder sb = new StringBuilder("<SOURCE>.get");
        Type optionalBaseType = conversionContext.getSourceType().getOptionalBaseType();
        if ( optionalBaseType.isPrimitive() ) {
            sb.append( "As" ).append( Strings.capitalize( optionalBaseType.getName() ) );
        }
        return sb.append( "()" ).toString();
    }
}
