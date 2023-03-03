/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping;

import java.util.Objects;
import java.util.Optional;

public class Source {

    private final Optional<String> constructorOptionalToNonOptional;
    private final String constructorNonOptionalToOptional;
    private final Optional<String> constructorOptionalToOptional;

    private final Optional<SubType> constructorOptionalSubTypeToNonOptional;
    private final SubType constructorNonOptionalSubTypeToOptional;
    private final Optional<SubType> constructorOptionalSubTypeToOptional;

    private Optional<String> optionalToNonOptional;
    private String nonOptionalToOptional;
    private Optional<String> optionalToOptional;

    private Optional<SubType> optionalSubTypeToNonOptional;
    private SubType nonOptionalSubTypeToOptional;
    private Optional<SubType> optionalSubTypeToOptional;

    private Optional<String> monitoredOptionalToString;
    private Optional<SubType> monitoredOptionalSubTypeToSubType;

    public Source(Optional<String> constructorOptionalToNonOptional,
                  String constructorNonOptionalToOptional,
                  Optional<String> constructorOptionalToOptional,
                  Optional<SubType> constructorOptionalSubTypeToNonOptional,
                  SubType constructorNonOptionalSubTypeToOptional,
                  Optional<SubType> constructorOptionalSubTypeToOptional) {
        this.constructorOptionalToNonOptional = constructorOptionalToNonOptional;
        this.constructorNonOptionalToOptional = constructorNonOptionalToOptional;
        this.constructorOptionalToOptional = constructorOptionalToOptional;
        this.constructorOptionalSubTypeToNonOptional = constructorOptionalSubTypeToNonOptional;
        this.constructorNonOptionalSubTypeToOptional = constructorNonOptionalSubTypeToOptional;
        this.constructorOptionalSubTypeToOptional = constructorOptionalSubTypeToOptional;
    }

    public Optional<String> getConstructorOptionalToNonOptional() {
        return constructorOptionalToNonOptional;
    }

    public String getConstructorNonOptionalToOptional() {
        return constructorNonOptionalToOptional;
    }

    public Optional<String> getConstructorOptionalToOptional() {
        return constructorOptionalToOptional;
    }

    public Optional<SubType> getConstructorOptionalSubTypeToNonOptional() {
        return constructorOptionalSubTypeToNonOptional;
    }

    public SubType getConstructorNonOptionalSubTypeToOptional() {
        return constructorNonOptionalSubTypeToOptional;
    }

    public Optional<SubType> getConstructorOptionalSubTypeToOptional() {
        return constructorOptionalSubTypeToOptional;
    }

    public Optional<String> getOptionalToNonOptional() {
        return optionalToNonOptional;
    }

    public void setOptionalToNonOptional(Optional<String> optionalToNonOptional) {
        this.optionalToNonOptional = optionalToNonOptional;
    }

    public String getNonOptionalToOptional() {
        return nonOptionalToOptional;
    }

    public void setNonOptionalToOptional(String nonOptionalToOptional) {
        this.nonOptionalToOptional = nonOptionalToOptional;
    }

    public Optional<String> getOptionalToOptional() {
        return optionalToOptional;
    }

    public void setOptionalToOptional(Optional<String> optionalToOptional) {
        this.optionalToOptional = optionalToOptional;
    }

    public Optional<SubType> getOptionalSubTypeToNonOptional() {
        return optionalSubTypeToNonOptional;
    }

    public void setOptionalSubTypeToNonOptional(Optional<SubType> optionalSubTypeToNonOptional) {
        this.optionalSubTypeToNonOptional = optionalSubTypeToNonOptional;
    }

    public SubType getNonOptionalSubTypeToOptional() {
        return nonOptionalSubTypeToOptional;
    }

    public void setNonOptionalSubTypeToOptional(SubType nonOptionalSubTypeToOptional) {
        this.nonOptionalSubTypeToOptional = nonOptionalSubTypeToOptional;
    }

    public Optional<SubType> getOptionalSubTypeToOptional() {
        return optionalSubTypeToOptional;
    }

    public void setOptionalSubTypeToOptional(Optional<SubType> optionalSubTypeToOptional) {
        this.optionalSubTypeToOptional = optionalSubTypeToOptional;
    }

    public Optional<String> getMonitoredOptionalToString() {
        return monitoredOptionalToString;
    }

    public void setMonitoredOptionalToString(Optional<String> monitoredOptionalToString) {
        this.monitoredOptionalToString = monitoredOptionalToString;
    }

    public Optional<SubType> getMonitoredOptionalSubTypeToSubType() {
        return monitoredOptionalSubTypeToSubType;
    }

    public void setMonitoredOptionalSubTypeToSubType(
        Optional<SubType> monitoredOptionalSubTypeToSubType) {
        this.monitoredOptionalSubTypeToSubType = monitoredOptionalSubTypeToSubType;
    }

    public static class SubType {

        private final int value;

        public SubType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if ( this == o ) {
                return true;
            }
            if ( o == null || getClass() != o.getClass() ) {
                return false;
            }
            SubType subType = (SubType) o;
            return value == subType.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash( value );
        }

    }

}
