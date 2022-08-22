/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2704;

/**
 * @author Valentin Kulesh
 */
public interface TopLevel {
    enum InnerEnum {
        VALUE1,
        VALUE2,
    }

    class Target {
        public void setE(@SuppressWarnings("unused") InnerEnum e) {
        }
    }
}
