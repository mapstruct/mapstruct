/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1707;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-02-10T09:58:11+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
public class ConverterImpl extends Converter {

    @Override
    public Set<Target> convert(Stream<Source> source) {
        if ( source == null ) {
            return null;
        }

        Set<Target> set = new LinkedHashSet<Target>();

        set.addAll( source.map( source1 -> convert( source1 ) )
            .collect( Collectors.toCollection( LinkedHashSet<Target>::new ) )
        );

        addCustomValue( set );

        return set;
    }

    @Override
    public org.mapstruct.ap.test.bugs._1707.Converter.Target[] convertArray(Stream<Source> source) {
        if ( source == null ) {
            return null;
        }

        org.mapstruct.ap.test.bugs._1707.Converter.Target[] targetTmp = null;

        targetTmp = source.map( source1 -> convert( source1 ) )
        .toArray( Target[]::new );

        addCustomValue( targetTmp );

        return targetTmp;
    }

    @Override
    public Target convert(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        target.setText( source.getText() );
        target.setNumber( source.getNumber() );

        return target;
    }
}
