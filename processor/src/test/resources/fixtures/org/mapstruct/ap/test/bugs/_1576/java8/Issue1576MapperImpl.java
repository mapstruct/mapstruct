/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1576.java8;

import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-11-04T00:08:04+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
public class Issue1576MapperImpl implements Issue1576Mapper {

    @Override
    public Target map(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        if ( source.getLdate() != null ) {
            target.setLdate( LocalDateTime.ofInstant( source.getLdate().toInstant(), ZoneId.of( "UTC" ) ) );
        }

        return target;
    }
}
