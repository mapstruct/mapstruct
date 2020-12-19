/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2301;

import java.util.Set;

/**
 * @author Filip Hrisafov
 */
public class Artifact {

    private String name;
    private Set<String> dependantBuildRecords;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getDependantBuildRecords() {
        return dependantBuildRecords;
    }

    public void setDependantBuildRecords(Set<String> dependantBuildRecords) {
        this.dependantBuildRecords = dependantBuildRecords;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private Set<String> dependantBuildRecords;

        public Artifact build() {
            Artifact artifact = new Artifact();
            artifact.setName( name );
            artifact.setDependantBuildRecords( dependantBuildRecords );

            return artifact;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder dependantBuildRecord(String dependantBuildRecord) {
            this.dependantBuildRecords.add( dependantBuildRecord );
            return this;
        }

        public Builder dependantBuildRecords(Set<String> dependantBuildRecords) {
            this.dependantBuildRecords = dependantBuildRecords;
            return this;
        }
    }
}
