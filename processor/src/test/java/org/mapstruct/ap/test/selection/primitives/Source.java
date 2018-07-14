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
public class Source {

    private int primitiveInt;
    private Integer wrappedInt;

    public int getPrimitiveInt() {
        return primitiveInt;
    }

    public void setPrimitiveInt( int primitiveInt ) {
        this.primitiveInt = primitiveInt;
    }

    public Integer getWrappedInt() {
        return wrappedInt;
    }

    public void setWrappedInt( Integer wrappedInt ) {
        this.wrappedInt = wrappedInt;
    }
}
