/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2677;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2677Mapper {

    Issue2677Mapper INSTANCE = Mappers.getMapper( Issue2677Mapper.class );

    @Mapping(target = "id", source = "value.id")
    Output map(Wrapper<? extends Parent> in);

    @Mapping(target = ".", source = "value")
    Output mapImplicitly(Wrapper<? extends Parent> in);

    @Mapping(target = "id", source = "value.id")
    Output mapFromParent(Wrapper<Parent> in);

    @Mapping(target = "id", source = "value.id")
    Output mapFromChild(Wrapper<Child> in);

    @Mapping( target = "value", source = "wrapperValue")
    Wrapper<String> mapToWrapper(String wrapperValue, Wrapper<? super Parent> wrapper);

    @Mapping(target = "id", source = "value.id")
    Output mapWithPresenceCheck(Wrapper<? extends ParentWithPresenceCheck> in);

    class Wrapper<T> {
        private final T value;
        private final String status;

        public Wrapper(T value, String status) {
            this.value = value;
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public T getValue() {
            return value;
        }
    }

    class Parent {
        private final int id;

        public Parent(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    class Child extends Parent {
        private final String whatever;

        public Child(int id, String whatever) {
            super( id );
            this.whatever = whatever;
        }

        public String getWhatever() {
            return whatever;
        }
    }

    class ParentWithPresenceCheck {
        private final int id;

        public ParentWithPresenceCheck(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public boolean hasId() {
            return id > 10;
        }
    }

    class Output {
        private int id;
        private String status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
