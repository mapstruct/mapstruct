/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * @author Filip Hrisafov
 *
 * @since 1.4
 */
public class DefaultEnumNamingStrategy implements EnumNamingStrategy {

    protected Elements elementUtils;
    protected Types typeUtils;

    @Override
    public void init(MapStructProcessingEnvironment processingEnvironment) {
        this.elementUtils = processingEnvironment.getElementUtils();
        this.typeUtils = processingEnvironment.getTypeUtils();
    }

    @Override
    public String getDefaultNullEnumConstant(TypeElement enumType) {
        return null;
    }

    @Override
    public String getEnumConstant(TypeElement enumType, String enumConstant) {
        return enumConstant;
    }

    @Override
    public TypeElement getDefaultExceptionType() {
        return elementUtils.getTypeElement( getDefaultExceptionClass().getCanonicalName() );
    }

    protected Class<? extends Exception> getDefaultExceptionClass() {
        return IllegalArgumentException.class;
    }
}
