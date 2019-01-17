/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseEntity {

    private final Long id;

    public BaseEntity(Builder builder) {
        this.id = builder.id;
        this.values = builder.values;
    }

    public Long getId() {
        return id;
    }

    private Map<String, String> values;

    public Map<String, String> getValues() {
        return values;
    }

    private List<String> others;

    public List<String> getOthers() {
        return this.others;
    }

    public static Builder baseBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Map<String, String> values = new HashMap<>();
        private List<String> others = new ArrayList<>();

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder addValues(String key, String value) {
            values.put( key, value );
            return this;
        }

        public Builder addOthers(String others) {
            this.others.add( others );
            return this;
        }

//        public Builder addValue(Map.Entry<String, String> entry) {
//            values.entrySet().add( entry );
//            return this;
//        }

//        public Builder putValue(String key, String value) {
//            values.put( key, value );
//            return this;
//        }
//
//        public Builder putValue(Map.Entry<String, String> entry) {
//            values.entrySet().add( entry );
//            return this;
//        }

        public BaseEntity build() {
            return new BaseEntity( this );
        }
    }
}
