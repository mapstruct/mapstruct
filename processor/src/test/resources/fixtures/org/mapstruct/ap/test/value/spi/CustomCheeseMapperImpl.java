/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.spi;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-05-16T12:53:12+0200",
    comments = "version: , compiler: javac, environment: Java 14.0.1 (Oracle Corporation)"
)
public class CustomCheeseMapperImpl implements CustomCheeseMapper {

    @Override
    public CheeseType map(CustomCheeseType cheese) {
        if ( cheese == null ) {
            return null;
        }

        CheeseType cheeseType;

        switch ( cheese ) {
            case UNSPECIFIED: cheeseType = null;
            break;
            case CUSTOM_BRIE: cheeseType = CheeseType.BRIE;
            break;
            case CUSTOM_ROQUEFORT: cheeseType = CheeseType.ROQUEFORT;
            break;
            case UNRECOGNIZED: cheeseType = null;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + cheese );
        }

        return cheeseType;
    }

    @Override
    public CustomCheeseType map(CheeseType cheese) {
        if ( cheese == null ) {
            return CustomCheeseType.UNSPECIFIED;
        }

        CustomCheeseType customCheeseType;

        switch ( cheese ) {
            case BRIE: customCheeseType = CustomCheeseType.CUSTOM_BRIE;
            break;
            case ROQUEFORT: customCheeseType = CustomCheeseType.CUSTOM_ROQUEFORT;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + cheese );
        }

        return customCheeseType;
    }

    @Override
    public String mapToString(CustomCheeseType cheeseType) {
        if ( cheeseType == null ) {
            return null;
        }

        String string;

        switch ( cheeseType ) {
            case UNSPECIFIED: string = null;
            break;
            case CUSTOM_BRIE: string = "BRIE";
            break;
            case CUSTOM_ROQUEFORT: string = "ROQUEFORT";
            break;
            case UNRECOGNIZED: string = null;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + cheeseType );
        }

        return string;
    }

    @Override
    public String mapToString(CheeseType cheeseType) {
        if ( cheeseType == null ) {
            return null;
        }

        String string;

        switch ( cheeseType ) {
            case BRIE: string = "BRIE";
            break;
            case ROQUEFORT: string = "ROQUEFORT";
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + cheeseType );
        }

        return string;
    }

    @Override
    public CustomCheeseType mapStringToCustom(String cheese) {
        if ( cheese == null ) {
            return CustomCheeseType.UNSPECIFIED;
        }

        CustomCheeseType customCheeseType;

        switch ( cheese ) {
            case "BRIE": customCheeseType = CustomCheeseType.CUSTOM_BRIE;
            break;
            case "ROQUEFORT": customCheeseType = CustomCheeseType.CUSTOM_ROQUEFORT;
            break;
            default: customCheeseType = CustomCheeseType.CUSTOM_BRIE;
        }

        return customCheeseType;
    }

    @Override
    public CheeseType mapStringToCheese(String cheese) {
        if ( cheese == null ) {
            return null;
        }

        CheeseType cheeseType;

        switch ( cheese ) {
            case "BRIE": cheeseType = CheeseType.BRIE;
            break;
            case "ROQUEFORT": cheeseType = CheeseType.ROQUEFORT;
            break;
            default: cheeseType = CheeseType.BRIE;
        }

        return cheeseType;
    }
}
