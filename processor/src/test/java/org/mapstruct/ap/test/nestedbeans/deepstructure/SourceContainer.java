/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.deepstructure;

public class SourceContainer {
    private Source source;
    private SourceCollectionContainer collectionContainer;
    private String otherField;

    public Source getSource() {
        return source;
    }

    public SourceCollectionContainer getCollectionContainer() {
        return collectionContainer;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public void setCollectionContainer(SourceCollectionContainer collectionContainer) {
        this.collectionContainer = collectionContainer;
    }

    public String getOtherField() {
        return otherField;
    }

    public void setOtherField(String otherField) {
        this.otherField = otherField;
    }
}