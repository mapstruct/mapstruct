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
public class ConflictingFruitFactory {

    public Apple createApple() {
        return new Apple( "apple" );
    }

    public Banana createBanana() {
        return new Banana( "banana" );
    }

}
