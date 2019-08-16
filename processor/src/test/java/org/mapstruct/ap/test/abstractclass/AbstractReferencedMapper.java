/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.abstractclass;

/**
 * @author Andreas Gudian
 */
public abstract class AbstractReferencedMapper implements ReferencedMapperInterface {

    @Override
    public int holderToInt(Holder<String> holder) {
        return 41;
    }

    public boolean objectToBoolean(Object obj) {
        return obj instanceof String;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals( obj );
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
