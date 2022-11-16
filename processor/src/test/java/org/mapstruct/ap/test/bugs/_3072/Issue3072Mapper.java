/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3072;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jarle Sætre
 */
@Mapper
public interface Issue3072Mapper {

    Issue3072Mapper INSTANCE = Mappers.getMapper( Issue3072Mapper.class );

    TargetWithRemover map(Source source);

    class Source {
        private List<String> strings = new ArrayList<>();

        public List<String> getStrings() {
            return strings;
        }

        public void setStrings(List<String> strings) {
            this.strings = strings;
        }
    }

    class TargetWithRemover {
        private List<String> strings = new ArrayList<>();

        public List<String> getStrings() {
            return strings;
        }

        public void setStrings(List<String> strings) {
            this.strings = strings;
        }

        @SuppressWarnings("unused")
        public TargetWithRemover addString(String string) {
            strings.add( string );
            return this;
        }

        @SuppressWarnings("unused")
        public TargetWithRemover removeString(String string) {
            strings.remove( string );
            return this;
        }
    }
}
