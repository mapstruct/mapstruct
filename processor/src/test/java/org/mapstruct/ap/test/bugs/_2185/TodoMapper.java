/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2185;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(uses = TodoMapper.class)
public interface TodoMapper {

    TodoMapper INSTANCE = Mappers.getMapper( TodoMapper.class );

    TodoResponse toResponse(TodoEntity entity);

    class TodoResponse {

        private final String note;

        public TodoResponse(String note) {
            this.note = note;
        }

        public String getNote() {
            return note;
        }
    }

    class TodoEntity {

        private final String note;

        public TodoEntity(String note) {
            this.note = note;
        }

        public String getNote() {
            return note;
        }
    }
}
