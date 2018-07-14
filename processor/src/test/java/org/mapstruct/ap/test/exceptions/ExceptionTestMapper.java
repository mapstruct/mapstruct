/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.exceptions;

import org.mapstruct.ap.test.exceptions.imports.TestException1;

/**
 * @author Sjaak Derksen
 *
 */
public class ExceptionTestMapper  {

    public Long toLong(Integer size) throws TestException1, TestException2 {
        if ( size == 1 ) {
            throw new TestException1();
        }
        else if ( size == 2 ) {
            throw new TestException2();
        }
        return new Long(size);
    }
}
