/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1519;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(implementationPackage = "<PACKAGE_NAME>.dest")
@DecoratedWith(Issue1519DefaultConstructorMapper.Decorator.class)
public interface Issue1519DefaultConstructorMapper {

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

    abstract class Decorator implements Issue1519DefaultConstructorMapper {

        @Override
        public Target map(Source source) {
            Target target = new Target();
            target.setValue( source.getValue() );
            return target;
        }
    }
}
