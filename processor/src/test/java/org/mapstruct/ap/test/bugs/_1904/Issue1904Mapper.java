/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1904;

import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue1904Mapper {

    CarManualDto translateManual(CarManual manual);

    /**
     * @author Filip Hrisafov
     */
    class CarManual {

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    /**
     * @author Filip Hrisafov
     */
    class CarManualDto {

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
