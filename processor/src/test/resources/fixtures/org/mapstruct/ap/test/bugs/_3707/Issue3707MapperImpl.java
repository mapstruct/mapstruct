/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3707;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-19T17:05:14+0300",
    comments = "version: , compiler: javac, environment: Java 17.0.10 (Private Build)"
)
public class Issue3707MapperImpl implements Issue3707Mapper {

    @Override
    public StringDto clone(StringDto stringDto) {
        if ( stringDto == null ) {
            return null;
        }

        StringDto.Builder stringDto1 = StringDto.builder();

        stringDto1.value( stringDto.getValue() );
        stringDto1.name( stringDto.getName() );
        stringDto1.id( stringDto.getId() );

        return stringDto1.build();
    }

    @Override
    public BigIntegerDto clone(BigIntegerDto bigIntegerDto) {
        if ( bigIntegerDto == null ) {
            return null;
        }

        BigIntegerDto.Builder bigIntegerDto1 = BigIntegerDto.builder();

        bigIntegerDto1.name( bigIntegerDto.getName() );
        bigIntegerDto1.id( bigIntegerDto.getId() );
        bigIntegerDto1.value( bigIntegerDto.getValue() );

        return bigIntegerDto1.build();
    }
}
