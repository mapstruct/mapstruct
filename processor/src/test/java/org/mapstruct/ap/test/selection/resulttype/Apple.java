/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.resulttype;

/**
 *
 * @author Sjaak Derksen
 */
public class Apple extends Fruit implements IsFruit {

    public Apple() {
        super( "constructed-by-constructor" );
    }

    public Apple(String type) {
        super( type );
    }
}
