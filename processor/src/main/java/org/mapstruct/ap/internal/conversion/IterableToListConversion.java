/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.mapstruct.ap.internal.conversion.ConversionUtils.collectors;
import static org.mapstruct.ap.internal.conversion.ConversionUtils.streamSupport;
import static org.mapstruct.ap.internal.util.Collections.asSet;

/**
 * Conversion between {@link Iterable} and {@link java.util.List}.
 *
 * @author Xiu-Hong Kooi
 */
public class IterableToListConversion extends SimpleConversion {
    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return streamSupport( conversionContext ) + ".stream( <SOURCE>.spliterator(), false )" +
                ".collect( " + collectors( conversionContext ) + ".toList() )";
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return "<SOURCE>";
    }

    @Override
    protected Set<Type> getToConversionImportTypes(final ConversionContext conversionContext) {
        return asSet( conversionContext.getTypeFactory().getType( StreamSupport.class ),
                conversionContext.getTypeFactory().getType( Collectors.class ) );
    }
}
