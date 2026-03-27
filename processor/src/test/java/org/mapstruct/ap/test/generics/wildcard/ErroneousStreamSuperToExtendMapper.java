/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.wildcard;

import org.mapstruct.Mapper;

import java.util.stream.Stream;

@Mapper
public interface ErroneousStreamSuperToExtendMapper {

    ExtendStreamTypes map(SuperStreamTypes types);

    class ExtendStreamTypes {
        private Stream<? extends SimpleObject> simpleObjectsStream;

        public Stream<? extends SimpleObject> getSimpleObjectsStream() {
            return simpleObjectsStream;
        }

        public void setSimpleObjectsStream(Stream<? extends SimpleObject> simpleObjectsStream) {
            this.simpleObjectsStream = simpleObjectsStream;
        }
    }

    class SuperStreamTypes {

        private Stream<? super SimpleObject> simpleObjectsStream;

        public Stream<? super SimpleObject> getSimpleObjectsStream() {
            return simpleObjectsStream;
        }

        public void setSimpleObjectsStream(Stream<? super SimpleObject> simpleObjectsStream) {
            this.simpleObjectsStream = simpleObjectsStream;
        }
    }
}
