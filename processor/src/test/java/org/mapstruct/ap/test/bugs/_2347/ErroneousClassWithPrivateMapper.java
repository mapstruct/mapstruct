/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2347;

import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
public class ErroneousClassWithPrivateMapper {

    public static class Target {
        private final String id;

        public Target(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public static class Source {

        private final String id;

        public Source(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    @Mapper
    private interface PrivateInterfaceMapper {

        Target map(Source source);
    }

    @Mapper
    private abstract class PrivateClassMapper {

        public abstract Target map(Source source);
    }
}
