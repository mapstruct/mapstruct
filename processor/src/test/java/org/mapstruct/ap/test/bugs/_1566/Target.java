/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1566;

/**
 * @author Filip Hrisafov
 */
public class Target {

    private final String id;
    private final String name;

    private Target(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends AbstractBuilder<Builder> {
        private String name;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Target build() {
            return new Target( id, name );
        }
    }
}
