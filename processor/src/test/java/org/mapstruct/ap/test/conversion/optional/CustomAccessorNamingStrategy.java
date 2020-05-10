/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.optional;

import org.mapstruct.ap.spi.AccessorNamingStrategy;
import org.mapstruct.ap.spi.DefaultAccessorNamingStrategy;

public class CustomAccessorNamingStrategy extends DefaultAccessorNamingStrategy implements AccessorNamingStrategy {

    @Override
    public String getPresenceCheckerMethodInType(String typeName) {
        if ( typeName.equals( "java.util.Optional" ) ) {
            return "isPresent";
        }
        return super.getPresenceCheckerMethodInType( typeName );
    }
}
