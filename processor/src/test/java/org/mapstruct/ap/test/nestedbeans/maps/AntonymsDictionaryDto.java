/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.maps;

import java.util.Map;
import java.util.Objects;

public class AntonymsDictionaryDto {
    private Map<WordDto, WordDto> antonyms;

    public AntonymsDictionaryDto() {
    }

    public AntonymsDictionaryDto(Map<WordDto, WordDto> antonyms) {
        this.antonyms = antonyms;
    }

    public Map<WordDto, WordDto> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(Map<WordDto, WordDto> antonyms) {
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

        AntonymsDictionaryDto antonymsDictionaryDto = (AntonymsDictionaryDto) o;

        return Objects.equals( antonyms, antonymsDictionaryDto.antonyms );

    }

    @Override
    public int hashCode() {
        return antonyms != null ? antonyms.hashCode() : 0;
    }
}
