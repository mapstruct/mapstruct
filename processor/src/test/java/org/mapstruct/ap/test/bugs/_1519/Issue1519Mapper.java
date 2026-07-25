/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1519;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper
@DecoratedWith(Issue1519Mapper.Decorator.class)
public interface Issue1519Mapper {

    Target map(Source source);

    class Source {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    class Target {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    abstract class Decorator implements Issue1519Mapper {

        private final Issue1519Mapper delegate;

        Decorator(Issue1519Mapper delegate) {
            this.delegate = delegate;
        }

        @Override
        public Target map(Source source) {
            return delegate.map( source );
        }
    }
}
