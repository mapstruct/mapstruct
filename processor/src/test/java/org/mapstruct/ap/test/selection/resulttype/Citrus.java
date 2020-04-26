/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.resulttype;

/**
 * @author Filip Hrisafov
 */
public class Citrus extends Fruit implements IsFruit {

    public Citrus(String type) {
        super( type );
    }

    @Override
    public void setType(String type) {
        throw new RuntimeException( "Not allowed to change citrus type" );
    }
}
