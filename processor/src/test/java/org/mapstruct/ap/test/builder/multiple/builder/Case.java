/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.multiple.builder;

/**
 * @author Filip Hrisafov
 */
public class Case {

    private final String builderCreationMethod;
    private final String buildMethod;
    private String name;

    public Case() {
        this.builderCreationMethod = null;
        this.buildMethod = "constructor";
    }

    public Case(Builder builder, String buildMethod) {
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

    public static Builder wrongBuilder() {
        return new Builder( "wrongBuilder" );
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

        public Case create() {
            return new Case( this, "create" );
        }
    }
}
