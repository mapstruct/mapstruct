/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.unmappable;

import java.util.Map;

public class DictionaryDto {

    private Map<WordDto, ForeignWordDto> wordMap;

    public Map<WordDto, ForeignWordDto> getWordMap() {
        return wordMap;
    }

    public void setWordMap(
        Map<WordDto, ForeignWordDto> wordMap) {
        this.wordMap = wordMap;
    }
}
