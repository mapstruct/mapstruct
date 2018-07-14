/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.handwritten;

/**
 *
 * @author Sjaak Derksen
 */
public class Reverse {

    public String reverse( String in ) {
        StringBuilder b = new StringBuilder(in);
        return b.reverse().toString();
    }

}
