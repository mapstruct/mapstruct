package org.mapstruct.ap.test.nestedbeans.deepstructure;

import java.util.List;

class SourceCollectionContainer {
    List<SourceCollectionItem> source;

    public List<SourceCollectionItem> getSource() {
        return source;
    }

    public void setSource(List<SourceCollectionItem> source) {
        this.source = source;
    }
}