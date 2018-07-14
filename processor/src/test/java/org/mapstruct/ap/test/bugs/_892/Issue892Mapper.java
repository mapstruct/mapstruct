/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._892;

import org.mapstruct.Mapper;

/**
 * @author Andreas Gudian
 *
 */
@Mapper
public interface Issue892Mapper {
    Target toTarget(Source source);

    class Source {
        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    class Target {
        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setType(String typeInHex) {
            this.type = Integer.parseInt( typeInHex, 16 );
        }
    }
}
