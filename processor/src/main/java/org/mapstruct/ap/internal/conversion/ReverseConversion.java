/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.util.List;

import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.FieldReference;

/**
 *  * A {@link ConversionProvider} which creates the inversed conversions for a
 * given conversion provider.
 *
 * @author Gunnar Morling
 */
public class ReverseConversion implements ConversionProvider {

    private ConversionProvider conversionProvider;

    public static ReverseConversion inverse(ConversionProvider conversionProvider) {
        return new ReverseConversion( conversionProvider );
    }

    private ReverseConversion(ConversionProvider conversionProvider) {
        this.conversionProvider = conversionProvider;
    }

    @Override
    public Assignment to(ConversionContext conversionContext) {
        return conversionProvider.from( conversionContext );
    }

    @Override
    public Assignment from(ConversionContext conversionContext) {
        return conversionProvider.to( conversionContext );
    }

    @Override
    public List<HelperMethod> getRequiredHelperMethods(ConversionContext conversionContext) {
        return conversionProvider.getRequiredHelperMethods( conversionContext );
    }

    @Override
    public List<FieldReference> getRequiredHelperFields(ConversionContext conversionContext) {
        return conversionProvider.getRequiredHelperFields( conversionContext );
    }
}
