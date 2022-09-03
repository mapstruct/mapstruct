/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2891;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-06-18T14:48:32+0300",
    comments = "version: , compiler: Eclipse JDT (Batch) 3.20.0.v20191203-2131, environment: Java 11.0.15.1 (BellSoft)"
)
public class Issue2891MapperImpl implements Issue2891Mapper {

    @Override
    public AbstractTarget map(AbstractSource source) {
        if ( source == null ) {
            return null;
        }

        if (source instanceof Source1) {
            return source1ToTarget1( (Source1) source );
        }
        else if (source instanceof Source2) {
            return source2ToTarget2( (Source2) source );
        }
        else {
            throw new IllegalArgumentException("Not all subclasses are supported for this mapping. Missing for " + source.getClass());
        }
    }

    protected Target1 source1ToTarget1(Source1 source1) {
        if ( source1 == null ) {
            return null;
        }

        String name = null;

        name = source1.getName();

        Target1 target1 = new Target1( name );

        return target1;
    }

    protected Target2 source2ToTarget2(Source2 source2) {
        if ( source2 == null ) {
            return null;
        }

        String name = null;

        name = source2.getName();

        Target2 target2 = new Target2( name );

        return target2;
    }
}
