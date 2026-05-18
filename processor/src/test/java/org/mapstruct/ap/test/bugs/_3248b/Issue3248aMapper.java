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
 * ignoreUnmappedSourceProperties of the parent does not propagate to the subclass via  @InheritConfiguration
 *
 * Diagnostics: [DiagnosticDescriptor: ERROR Issue3248aMapper.java:34
 * Unmapped source property: "value1Ignore".]]  expected: SUCCEEDED but was: FAILED]
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface Issue3248aMapper {

    @Mapping( target = "value1", source = "value1")
    @BeanMapping(ignoreUnmappedSourceProperties = "value1Ignore")
    Target1 map1(Source1 source);

    @InheritConfiguration
    @Mapping( target = "value2", source = "value2")
    @BeanMapping(ignoreUnmappedSourceProperties = "value2Ignore")
    Target2 map2(Source2 source2); // fails to ignore "value1Ignore"

    class Target1 {
      private String value1;

      public String getValue1() {
         return value1;
      }

      public void setValue1(String value1) {
         this.value1 = value1;
      }
    }

    class Source1 {
      private String value1;
      private String value1Ignore;

      public String getValue1() {
         return value1;
      }

      public String getValue1Ignore() {
         return value1Ignore;
      }
    }

    class Target2 extends Target1 {
      private String value2;

      public String getValue2() {
         return value2;
      }

      public void setValue2(String value2) {
         this.value2 = value2;
      }
    }

    class Source2 extends Source1 {
      private String value2;
      private String value2Ignore;

      public String getValue2() {
         return value2;
      }

      public String getValue2Ignore() {
         return value2Ignore;
      }
    }
}
