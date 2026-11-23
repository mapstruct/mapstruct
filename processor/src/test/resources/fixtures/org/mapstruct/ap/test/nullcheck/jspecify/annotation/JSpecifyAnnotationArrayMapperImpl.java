/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.annotation;

import javax.annotation.processing.Generated;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-11-23T22:11:46+0100",
    comments = "version: , compiler: javac, environment: Java 21.0.13 (Eclipse Adoptium)"
)
@NullMarked
public class JSpecifyAnnotationArrayMapperImpl implements JSpecifyAnnotationArrayMapper {

    @Override
    public @Nullable Long[] fromStringArray(String @Nullable [] s) {
        if ( s == null ) {
            return new Long[0];
        }

        Long[] longTmp = new Long[s.length];
        int i = 0;
        for ( String string : s ) {
            longTmp[i] = Long.parseLong( string );
            i++;
        }

        return longTmp;
    }

    @Override
    public Long[] @Nullable [] fromStringArray2(@Nullable String @Nullable [][] s) {
        if ( s == null ) {
            return new Long[0][];
        }

        Long[][] longArrayTmp = new Long[s.length][];
        int i = 0;
        for ( String[] stringArray : s ) {
            longArrayTmp[i] = fromStringArray( stringArray );
            i++;
        }

        return longArrayTmp;
    }

    @Override
    public Long[] @Nullable [][] fromStringArray3(@Nullable String @Nullable [][] @Nullable [] s) {
        if ( s == null ) {
            return new Long[0][][];
        }

        Long[][][] longArrayArrayTmp = new Long[s.length][][];
        int i = 0;
        for ( String[][] stringArrayArray : s ) {
            longArrayArrayTmp[i] = fromStringArray2( stringArrayArray );
            i++;
        }

        return longArrayArrayTmp;
    }

    @Override
    public @Nullable Long[] @Nullable [][] fromStringArray3V2(@Nullable String[][] @Nullable [] s) {

        Long[][][] longArrayArrayTmp = new Long[s.length][][];
        int i = 0;
        for ( String[][] stringArrayArray : s ) {
            longArrayArrayTmp[i] = fromStringArray2( stringArrayArray );
            i++;
        }

        return longArrayArrayTmp;
    }
}
