/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1244;

import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface SizeMapper {

    SizeHolderDto convert(SizeHolder size);

    SizeHolderDto convert(SizeHolder size, int test);

    SizeHolderDto convertOther(SizeHolder sizeHolder, int size);

    class SizeHolder {
        private String size;

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }

    class SizeHolderDto {
        private String size;

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }
}
