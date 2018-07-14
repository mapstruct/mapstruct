/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritance.complex;

public class AdditionalMappingHelper {

    public Reference asReference(SourceBase source) {
        if ( null == source ) {
            return null;
        }

        Reference result = new Reference();
        result.setFoo( source.getFoo() );

        return result;
    }

    public Reference asReference(AdditionalFooSource source) {
        if ( null == source ) {
            return null;
        }

        Reference result = new Reference();
        result.setFoo( source.getAdditionalFoo() );

        return result;
    }
}
