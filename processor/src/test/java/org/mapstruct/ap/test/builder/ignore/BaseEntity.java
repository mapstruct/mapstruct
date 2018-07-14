/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.ignore;

/**
 * @author Filip Hrisafov
 */
public class BaseEntity {

    private final Long id;

    public BaseEntity(Builder builder) {
        this.id = builder.id;
    }

    public Long getId() {
        return id;
    }

    public static Builder baseBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public BaseEntity createBase() {
            return new BaseEntity( this );
        }
    }
}
