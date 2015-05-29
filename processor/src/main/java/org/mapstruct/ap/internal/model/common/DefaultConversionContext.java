/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
    private final String dateFormat;
    private final TypeFactory typeFactory;

    public DefaultConversionContext(TypeFactory typeFactory, FormattingMessager messager, Type sourceType,
                                    Type targetType, String dateFormat) {
        this.typeFactory = typeFactory;
        this.messager = messager;
        this.sourceType = sourceType;
        this.targetType = targetType;
        this.dateFormat = dateFormat;
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
                validationResult.printErrorMessage( messager );
            }
        }
    }

    @Override
    public Type getTargetType() {
        return targetType;
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
