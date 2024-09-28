/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3248b;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * @author RichMacDonald
 *
 * Adding inheritance to issue 3248
 *
 * Unmapped source properties of the parent do not propagate to the subclass:
 *
 * Diagnostics: [DiagnosticDescriptor: ERROR Issue3248aMapper.java:30 Unmapped source property: "otherValue".,
 * DiagnosticDescriptor: ERROR Issue3248aMapper.java:33 Unmapped source property: "otherValue".]]
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface Issue3248aMapper {

    @BeanMapping(ignoreUnmappedSourceProperties = "otherValue")
    Target map1(Source source);

    @InheritConfiguration(name = "map1")
    Target secondMap(Source source);

    @Mapping( target = "value2", source = "value2")
    @BeanMapping(ignoreUnmappedSourceProperties = "otherValue2")
    Target2 map2(Source2 source2); // fails to ignore "otherValue"

    @InheritConfiguration(name = "map2")
    Target2 secondMap2(Source2 source2);

    class Target {
        protected final String value;

        public Target(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    class Source {
      protected final String value;

        public Source(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getOtherValue() {
            return value;
        }
    }

    class Target2 extends Target {

      public Target2(String value, String value2) {
          super( value );
      }

      public String getValue2() {
          return value;
      }
  }

    class Source2 extends Source {

        public Source2(String value) {
          super( value );
        }

        public String getValue2() {
            return value;
        }

        public String getOtherValue2() {
            return value;
        }
    }
}
