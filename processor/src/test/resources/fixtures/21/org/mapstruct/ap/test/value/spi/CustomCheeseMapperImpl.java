/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.spi;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-12T14:19:17+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.11 (Eclipse Adoptium)"
)
public class CustomCheeseMapperImpl implements CustomCheeseMapper {

    @Override
    public CheeseType map(CustomCheeseType cheese) {
        if ( cheese == null ) {
            return null;
        }

        CheeseType cheeseType = switch ( cheese ) {
            case UNSPECIFIED -> null;
            case CUSTOM_BRIE -> CheeseType.BRIE;
            case CUSTOM_ROQUEFORT -> CheeseType.ROQUEFORT;
            case UNRECOGNIZED -> null;
        };

        return cheeseType;
    }

    @Override
    public CustomCheeseType map(CheeseType cheese) {
        if ( cheese == null ) {
            return CustomCheeseType.UNSPECIFIED;
        }

        CustomCheeseType customCheeseType = switch ( cheese ) {
            case BRIE -> CustomCheeseType.CUSTOM_BRIE;
            case ROQUEFORT -> CustomCheeseType.CUSTOM_ROQUEFORT;
        };

        return customCheeseType;
    }

    @Override
    public String mapToString(CustomCheeseType cheeseType) {
        if ( cheeseType == null ) {
            return null;
        }

        String string = switch ( cheeseType ) {
            case UNSPECIFIED -> null;
            case CUSTOM_BRIE -> "BRIE";
            case CUSTOM_ROQUEFORT -> "ROQUEFORT";
            case UNRECOGNIZED -> null;
        };

        return string;
    }

    @Override
    public String mapToString(CheeseType cheeseType) {
        if ( cheeseType == null ) {
            return null;
        }

        String string = switch ( cheeseType ) {
            case BRIE -> "BRIE";
            case ROQUEFORT -> "ROQUEFORT";
        };

        return string;
    }

    @Override
    public CustomCheeseType mapStringToCustom(String cheese) {
        if ( cheese == null ) {
            return CustomCheeseType.UNSPECIFIED;
        }

        CustomCheeseType customCheeseType = switch ( cheese ) {
            case "BRIE" -> CustomCheeseType.CUSTOM_BRIE;
            case "ROQUEFORT" -> CustomCheeseType.CUSTOM_ROQUEFORT;
            default -> CustomCheeseType.CUSTOM_BRIE;
        };

        return customCheeseType;
    }

    @Override
    public CheeseType mapStringToCheese(String cheese) {
        if ( cheese == null ) {
            return null;
        }

        CheeseType cheeseType = switch ( cheese ) {
            case "BRIE" -> CheeseType.BRIE;
            case "ROQUEFORT" -> CheeseType.ROQUEFORT;
            default -> CheeseType.BRIE;
        };

        return cheeseType;
    }
}
