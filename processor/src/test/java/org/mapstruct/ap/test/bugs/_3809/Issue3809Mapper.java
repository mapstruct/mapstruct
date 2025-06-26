/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3809;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetPropertyName;

@Mapper
public interface Issue3809Mapper {
    void updateMappingFails(Source source, @MappingTarget Target target);

    @Condition
    default boolean canMap(Object source, @TargetPropertyName String propertyName) {
        return true;
    }

    class Source {
        private NestedSource param;

        public NestedSource getParam() {
            return param;
        }
    }

    class NestedSource {
        private String param1;

        public String getParam1() {
            return param1;
        }

        public void setParam1(String param1) {
            this.param1 = param1;
        }

    }

    class Target {

        private NestedTarget param;

        public NestedTarget getParam() {
            return param;
        }

        public void setParam(NestedTarget param) {
            this.param = param;
        }

    }

    class NestedTarget {
        private String param1;

        public String getParam1() {
            return param1;
        }

        public void setParam1(String param1) {
            this.param1 = param1;
        }

    }
}
