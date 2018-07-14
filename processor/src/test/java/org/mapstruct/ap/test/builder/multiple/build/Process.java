/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.multiple.build;

/**
 * @author Filip Hrisafov
 */
public class Process {

    private final String builderCreationMethod;
    private final String buildMethod;
    private String name;

    public Process() {
        this.builderCreationMethod = null;
        this.buildMethod = "constructor";
    }

    public Process(Builder builder, String buildMethod) {
        this.builderCreationMethod = builder.builderCreationMethod;
        this.buildMethod = buildMethod;
        this.name = builder.name;
    }

    public String getBuilderCreationMethod() {
        return builderCreationMethod;
    }

    public String getBuildMethod() {
        return buildMethod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Builder builder() {
        return new Builder( "builder" );
    }

    public static class Builder {

        private String name;
        private String builderCreationMethod;

        protected Builder(String builderCreationMethod) {
            this.builderCreationMethod = builderCreationMethod;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Process wrongCreate() {
            return new Process( this, "wrongCreate" );
        }

        public Process create() {
            return new Process( this, "create" );
        }

    }
}
