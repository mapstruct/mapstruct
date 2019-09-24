/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.naming;

import javax.lang.model.element.ExecutableElement;

import org.mapstruct.ap.spi.AccessorNamingStrategy;
import org.mapstruct.ap.spi.DefaultAccessorNamingStrategy;
import org.mapstruct.ap.spi.util.IntrospectorUtils;

/**
 * A custom {@link AccessorNamingStrategy} recognizing presence checkers in the form of {@code hasNoProperty()}.
 *
 * @author Kirill Baurchanu
 */
public class CustomAccessorNamingStrategy extends DefaultAccessorNamingStrategy implements AccessorNamingStrategy {

    @Override
    public boolean isPresenceCheckMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();
        return methodName.startsWith( "hasNo" ) && methodName.length() > 5;
    }

    @Override
    public String getPropertyName(ExecutableElement getterOrSetterMethod) {
        String methodName = getterOrSetterMethod.getSimpleName().toString();
        if ( isFluentSetter( getterOrSetterMethod ) ) {
            if ( methodName.startsWith( "set" )
                && methodName.length() > 3
                && Character.isUpperCase( methodName.charAt( 3 ) ) ) {
                return IntrospectorUtils.decapitalize( methodName.substring( 3 ) );
            }
            else {
                return methodName;
            }
        }
        return IntrospectorUtils.decapitalize( methodName.substring( getBeginIndexOfPropertyName( methodName ) ) );
    }

    private int getBeginIndexOfPropertyName(String methodName) {
        if ( methodName.startsWith( "hasNo" ) ) {
            return 5;
        }
        return methodName.startsWith( "is" ) ? 2 : 3;
    }

}
