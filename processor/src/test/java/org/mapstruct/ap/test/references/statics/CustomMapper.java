/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.references.statics;

/**
 *
 * @author Sjaak Derksen
 */
public class CustomMapper {

    private CustomMapper() {
    }

    public static Category toCategory(float in) {

        if ( in < 2.5 ) {
            return Category.LIGHT;
        }
        else if ( in < 5.5 ) {
            return Category.LAGER;
        }
        else if ( in < 10 ) {
            return Category.STRONG;
        }
        else {
            return Category.BARLEY_WINE;
        }
    }
}
