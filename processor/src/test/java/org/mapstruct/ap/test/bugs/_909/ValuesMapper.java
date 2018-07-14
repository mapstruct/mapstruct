/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._909;

import org.mapstruct.Mapper;

/**
 * @author Andreas Gudian
 */
@Mapper
public interface ValuesMapper {
    ValuesHolderDto convert(ValuesHolder values);

    ValuesHolderDto convert(ValuesHolder values, int test);

    ValuesHolderDto convertOther(ValuesHolder valuesHolder, int values);

    class ValuesHolder {
        private String values;

        public String getValues() {
            return values;
        }

        public void setValues(String values) {
            this.values = values;
        }
    }

    class ValuesHolderDto {
        private String values;

        public String getValues() {
            return values;
        }

        public void setValues(String values) {
            this.values = values;
        }
    }
}
