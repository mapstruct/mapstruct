/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NullMarked;
import org.mapstruct.Named;

@NullMarked
public class JSpecifySimpleReusedMethodUses {

    @Named("map")
    public JSpecifySimpleReusedMethodValue map(JSpecifySimpleReusedMethodValue source) {
        return new JSpecifySimpleReusedMethodValue( source.value() );
    }
}
