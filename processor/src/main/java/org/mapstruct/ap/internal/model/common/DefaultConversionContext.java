/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Strings;

/**
 * Default implementation of the {@link ConversionContext} passed to conversion providers.
 *
 * @author Gunnar Morling
 */
public class DefaultConversionContext implements ConversionContext {

    private final FormattingMessager messager;
    private final Type sourceType;
    private final Type targetType;
    private final FormattingParameters formattingParameters;
    private final String dateFormat;
    private final String numberFormat;
    private final String locale;
    private final TypeFactory typeFactory;

    public DefaultConversionContext(TypeFactory typeFactory, FormattingMessager messager, Type sourceType,
                                    Type targetType, FormattingParameters formattingParameters) {
        this.typeFactory = typeFactory;
        this.messager = messager;
        this.sourceType = sourceType;
        this.targetType = targetType;
        this.formattingParameters = formattingParameters;
        this.dateFormat = this.formattingParameters.getDate();
        this.numberFormat = this.formattingParameters.getNumber();
        this.locale = this.formattingParameters.getLocale();
        validateDateFormat();
    }

    /**
     * Validate the dateFormat if it is not null
     */
    private void validateDateFormat() {
        if ( !Strings.isEmpty( dateFormat ) ) {
            DateFormatValidator dateFormatValidator = DateFormatValidatorFactory.forTypes( sourceType, targetType );
            DateFormatValidationResult validationResult = dateFormatValidator.validate( dateFormat );

            if ( !validationResult.isValid() ) {
                validationResult.printErrorMessage(
                    messager,
                    formattingParameters.getElement(),
                    formattingParameters.getMirror(),
                    formattingParameters.getDateAnnotationValue()
                );
            }
        }
    }

    @Override
    public Type getTargetType() {
        return targetType;
    }

    @Override
    public String getNumberFormat() {
        return numberFormat;
    }

    @Override
    public String getLocale() {
        return locale != null ? locale.toString() : null;
    }

    @Override
    public String getDateFormat() {
        return dateFormat;
    }

    @Override
    public TypeFactory getTypeFactory() {
        return typeFactory;
    }

    protected FormattingMessager getMessager() {
        return messager;
    }
}
