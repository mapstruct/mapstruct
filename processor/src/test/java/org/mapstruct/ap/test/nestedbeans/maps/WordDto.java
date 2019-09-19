/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.maps;

import java.util.Objects;

public class WordDto {
    private String textValue;

    public WordDto() {
    }

    public WordDto(String textValue) {
        this.textValue = textValue;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        WordDto wordDto = (WordDto) o;

        return Objects.equals( textValue, wordDto.textValue );

    }

    @Override
    public int hashCode() {
        return textValue != null ? textValue.hashCode() : 0;
    }
}
