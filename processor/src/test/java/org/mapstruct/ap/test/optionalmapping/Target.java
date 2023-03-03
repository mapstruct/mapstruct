/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping;

import java.util.Objects;
import java.util.Optional;

public class Target {

    private final String constructorOptionalToNonOptional;
    private final Optional<String> constructorNonOptionalToOptional;
    private final Optional<String> constructorOptionalToOptional;

    private final SubType constructorOptionalSubTypeToNonOptional;
    private final Optional<SubType> constructorNonOptionalSubTypeToOptional;
    private final Optional<SubType> constructorOptionalSubTypeToOptional;

    private String optionalToNonOptional;
    private Optional<String> nonOptionalToOptional;
    private Optional<String> optionalToOptional;

    private SubType optionalSubTypeToNonOptional;
    private Optional<SubType> nonOptionalSubTypeToOptional;
    private Optional<SubType> optionalSubTypeToOptional;

    private String monitoredOptionalToString;
    private boolean monitoredOptionalToStringWasCalled;
    private SubType monitoredOptionalSubTypeToSubType;
    private boolean monitoredOptionalSubTypeToSubTypeWasCalled;

    public Optional<SubType> publicOptionalSubTypeToOptional;
    public String publicOptionalToNonOptionalWithDefault = "DEFAULT";

    public Target(String constructorOptionalToNonOptional,
                  Optional<String> constructorNonOptionalToOptional,
                  Optional<String> constructorOptionalToOptional,
                  SubType constructorOptionalSubTypeToNonOptional,
                  Optional<SubType> constructorNonOptionalSubTypeToOptional,
                  Optional<SubType> constructorOptionalSubTypeToOptional) {
        this.constructorOptionalToNonOptional = constructorOptionalToNonOptional;
        this.constructorNonOptionalToOptional = constructorNonOptionalToOptional;
        this.constructorOptionalToOptional = constructorOptionalToOptional;
        this.constructorOptionalSubTypeToNonOptional = constructorOptionalSubTypeToNonOptional;
        this.constructorNonOptionalSubTypeToOptional = constructorNonOptionalSubTypeToOptional;
        this.constructorOptionalSubTypeToOptional = constructorOptionalSubTypeToOptional;
    }

    public String getConstructorOptionalToNonOptional() {
        return constructorOptionalToNonOptional;
    }

    public Optional<String> getConstructorNonOptionalToOptional() {
        return constructorNonOptionalToOptional;
    }

    public Optional<String> getConstructorOptionalToOptional() {
        return constructorOptionalToOptional;
    }

    public SubType getConstructorOptionalSubTypeToNonOptional() {
        return constructorOptionalSubTypeToNonOptional;
    }

    public Optional<SubType> getConstructorNonOptionalSubTypeToOptional() {
        return constructorNonOptionalSubTypeToOptional;
    }

    public Optional<SubType> getConstructorOptionalSubTypeToOptional() {
        return constructorOptionalSubTypeToOptional;
    }

    public String getOptionalToNonOptional() {
        return optionalToNonOptional;
    }

    public void setOptionalToNonOptional(String optionalToNonOptional) {
        this.optionalToNonOptional = optionalToNonOptional;
    }

    public Optional<String> getNonOptionalToOptional() {
        return nonOptionalToOptional;
    }

    public void setNonOptionalToOptional(Optional<String> nonOptionalToOptional) {
        this.nonOptionalToOptional = nonOptionalToOptional;
    }

    public Optional<String> getOptionalToOptional() {
        return optionalToOptional;
    }

    public void setOptionalToOptional(Optional<String> optionalToOptional) {
        this.optionalToOptional = optionalToOptional;
    }

    public SubType getOptionalSubTypeToNonOptional() {
        return optionalSubTypeToNonOptional;
    }

    public void setOptionalSubTypeToNonOptional(SubType optionalSubTypeToNonOptional) {
        this.optionalSubTypeToNonOptional = optionalSubTypeToNonOptional;
    }

    public Optional<SubType> getNonOptionalSubTypeToOptional() {
        return nonOptionalSubTypeToOptional;
    }

    public void setNonOptionalSubTypeToOptional(Optional<SubType> nonOptionalSubTypeToOptional) {
        this.nonOptionalSubTypeToOptional = nonOptionalSubTypeToOptional;
    }

    public Optional<SubType> getOptionalSubTypeToOptional() {
        return optionalSubTypeToOptional;
    }

    public void setOptionalSubTypeToOptional(Optional<SubType> optionalSubTypeToOptional) {
        this.optionalSubTypeToOptional = optionalSubTypeToOptional;
    }

    public String getMonitoredOptionalToString() {
        return monitoredOptionalToString;
    }

    public void setMonitoredOptionalToString(String monitoredOptionalToString) {
        this.monitoredOptionalToStringWasCalled = true;
        this.monitoredOptionalToString = monitoredOptionalToString;
    }

    public SubType getMonitoredOptionalSubTypeToSubType() {
        return monitoredOptionalSubTypeToSubType;
    }

    public void setMonitoredOptionalSubTypeToSubType(
        SubType monitoredOptionalSubTypeToSubType) {
        this.monitoredOptionalSubTypeToSubTypeWasCalled = true;
        this.monitoredOptionalSubTypeToSubType = monitoredOptionalSubTypeToSubType;
    }

    public boolean isMonitoredOptionalToStringWasCalled() {
        return monitoredOptionalToStringWasCalled;
    }

    public boolean isMonitoredOptionalSubTypeToSubTypeWasCalled() {
        return monitoredOptionalSubTypeToSubTypeWasCalled;
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
