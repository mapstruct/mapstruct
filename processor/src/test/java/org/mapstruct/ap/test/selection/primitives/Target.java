/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.primitives;

/**
 *
 * @author Sjaak Derksen
 */
public class Target {

    private MyLong primitiveInt;
    private MyLong wrappedInt;

    public MyLong getPrimitiveInt() {
        return primitiveInt;
    }

    public void setPrimitiveInt( MyLong primitiveInt ) {
        this.primitiveInt = primitiveInt;
    }

    public MyLong getWrappedInt() {
        return wrappedInt;
    }

    public void setWrappedInt( MyLong wrappedInt ) {
        this.wrappedInt = wrappedInt;
    }

}
