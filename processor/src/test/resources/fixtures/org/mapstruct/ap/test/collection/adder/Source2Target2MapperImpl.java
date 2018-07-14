/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.adder;

import javax.annotation.Generated;
import org.mapstruct.ap.test.collection.adder._target.Target2;
import org.mapstruct.ap.test.collection.adder.source.Foo;
import org.mapstruct.ap.test.collection.adder.source.Source2;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2016-12-30T19:10:39+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_112 (Oracle Corporation)"
)
public class Source2Target2MapperImpl extends Source2Target2Mapper {

    @Override
    public Target2 toTarget(Source2 source) {
        if ( source == null ) {
            return null;
        }

        Target2 target2 = new Target2();

        if ( source.getAttributes() != null ) {
            for ( Foo attribute : source.getAttributes() ) {
                target2.addAttribute( attribute );
            }
        }

        return target2;
    }
}
