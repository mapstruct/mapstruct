/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.bean;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Sjaak Derksen
 */
public abstract class AbstractEntry {

    private String title;

    private List<String> keyWords;

    private Map<String, List<String>> facts;

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords( List<String> keyWords ) {
        this.keyWords = keyWords;
    }

    public Map<String, List<String> > getFacts() {
        return facts;
    }

    public void setFacts( Map<String, List<String> > facts ) {
        this.facts = facts;
    }
}
