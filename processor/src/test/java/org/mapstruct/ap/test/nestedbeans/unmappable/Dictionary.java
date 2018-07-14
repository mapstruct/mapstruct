/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.unmappable;

import java.util.Map;

public class Dictionary {

    private Map<Word, ForeignWord> wordMap;

    public Map<Word, ForeignWord> getWordMap() {
        return wordMap;
    }

    public void setWordMap(
        Map<Word, ForeignWord> wordMap) {
        this.wordMap = wordMap;
    }
}
