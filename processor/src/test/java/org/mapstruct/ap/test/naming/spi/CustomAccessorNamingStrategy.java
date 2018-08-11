/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.naming.spi;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;

import org.mapstruct.ap.spi.util.IntrospectorUtils;
import org.mapstruct.ap.spi.AccessorNamingStrategy;
import org.mapstruct.ap.spi.DefaultAccessorNamingStrategy;

/**
 * A custom {@link AccessorNamingStrategy} recognizing getters in the form of {@code property()} and setters in the
 * form of {@code withProperty(value)}.
 *
 * @author Gunnar Morling
 */
public class CustomAccessorNamingStrategy extends DefaultAccessorNamingStrategy implements AccessorNamingStrategy {

    @Override
    public boolean isGetterMethod(ExecutableElement method) {
        return method.getReturnType().getKind() != TypeKind.VOID;
    }

    @Override
    public boolean isSetterMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();

        return methodName.startsWith( "with" ) && methodName.length() > 4;
    }

    @Override
    public boolean isAdderMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();
        return methodName.startsWith( "add" ) && methodName.length() > 3;
    }

    @Override
    public String getPropertyName(ExecutableElement getterOrSetterMethod) {
        String methodName = getterOrSetterMethod.getSimpleName().toString();
        return IntrospectorUtils.decapitalize(
            methodName.startsWith( "with" ) ? methodName.substring( 4 ) : methodName );
    }

    @Override
    public String getElementName(ExecutableElement adderMethod) {
        String methodName = adderMethod.getSimpleName().toString();
        return IntrospectorUtils.decapitalize( methodName.substring( 3 ) );
    }

}
