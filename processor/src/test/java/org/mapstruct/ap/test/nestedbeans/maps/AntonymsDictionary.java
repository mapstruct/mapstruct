/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.maps;

import java.util.Map;
import java.util.Objects;

public class AntonymsDictionary {
    private Map<Word, Word> antonyms;

    public AntonymsDictionary() {
    }

    public AntonymsDictionary(Map<Word, Word> antonyms) {
        this.antonyms = antonyms;
    }

    public Map<Word, Word> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(Map<Word, Word> antonyms) {
        this.antonyms = antonyms;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        AntonymsDictionary antonymsDictionary = (AntonymsDictionary) o;

        return Objects.equals( antonyms, antonymsDictionary.antonyms );

    }

    @Override
    public int hashCode() {
        return antonyms != null ? antonyms.hashCode() : 0;
    }
}
