/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.util.List;

import org.mapstruct.ap.internal.model.FromOptionalTypeConversion;
import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.ToOptionalTypeConversion;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.FieldReference;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;

/**
 * @author Filip Hrisafov
 */
public class OptionalWrapperConversionProvider implements ConversionProvider {

    private final ConversionProvider conversionProvider;

    public OptionalWrapperConversionProvider(ConversionProvider conversionProvider) {
        this.conversionProvider = conversionProvider;
    }

    @Override
    public Assignment to(ConversionContext conversionContext) {
        Assignment assignment = conversionProvider.to( new OptionalConversionContext( conversionContext ) );
        return new ToOptionalTypeConversion( conversionContext.getTargetType(), assignment );
    }

    @Override
    public Assignment from(ConversionContext conversionContext) {
        Assignment assignment = conversionProvider.to( new OptionalConversionContext( conversionContext ) );
        return new FromOptionalTypeConversion( conversionContext.getSourceType(), assignment );
    }

    @Override
    public List<HelperMethod> getRequiredHelperMethods(ConversionContext conversionContext) {
        return conversionProvider.getRequiredHelperMethods( conversionContext );
    }

    @Override
    public List<FieldReference> getRequiredHelperFields(ConversionContext conversionContext) {
        return conversionProvider.getRequiredHelperFields( conversionContext );
    }

    private static class OptionalConversionContext implements ConversionContext {

        private final ConversionContext delegate;

        private OptionalConversionContext(ConversionContext delegate) {
            this.delegate = delegate;
        }

        @Override
        public Type getTargetType() {
            return resolveType( delegate.getTargetType() );
        }

        @Override
        public Type getSourceType() {
            return resolveType( delegate.getSourceType() );
        }

        private Type resolveType(Type type) {
            if ( type.isOptionalType() ) {
                return type.getOptionalBaseType();
            }
            return type;
        }

        @Override
        public String getDateFormat() {
            return delegate.getDateFormat();
        }

        @Override
        public String getNumberFormat() {
            return delegate.getNumberFormat();
        }

        @Override
        public String getLocale() {
            return delegate.getLocale();
        }

        @Override
        public TypeFactory getTypeFactory() {
            return delegate.getTypeFactory();
        }
    }
}
