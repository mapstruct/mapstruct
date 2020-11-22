/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2197;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-02-10T09:58:11+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
public class Issue2197MapperImpl implements Issue2197Mapper {

    @Override
    public _0Target map(Source source) {
        if ( source == null ) {
            return null;
        }

        String value = null;

        value = source.getValue();

        _0Target target = new _0Target( value );

        return target;
    }
}
