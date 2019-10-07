/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1788;

import org.mapstruct.Mapper;

@Mapper
public interface Issue1788Mapper {

    Container toContainer(Container.Type type);

    class Container {
        public enum Type {
            ONE, TWO
        }

        //CHECKSTYLE:OFF
        public Type type;
        //CHECKSTYLE:ON
    }
}
