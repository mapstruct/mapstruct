/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.nestedbeans.maps;

import java.util.Map;

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

        return antonyms != null ? antonyms.equals( antonymsDictionaryDto.antonyms ) :
            antonymsDictionaryDto.antonyms == null;

    }

    @Override
    public int hashCode() {
        return antonyms != null ? antonyms.hashCode() : 0;
    }
}
