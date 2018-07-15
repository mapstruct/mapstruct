/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1541;

public class Target {
    private String code;
    private String[] parameters;
    private String[] parameters2;

    private boolean afterMappingWithVarArgsCalled = false;
    private boolean afterMappingWithArrayCalled = false;
    private boolean afterMappingContextWithVarArgsAsVarArgsCalled = false;
    private boolean afterMappingContextWithVarArgsAsArrayCalled = false;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    public String[] getParameters2() {
        return parameters2;
    }

    public void setParameters2(String[] parameters2) {
        this.parameters2 = parameters2;
    }

    public boolean isAfterMappingWithVarArgsCalled() {
        return afterMappingWithVarArgsCalled;
    }

    public void setAfterMappingWithVarArgsCalled(boolean afterMappingWithVarArgsCalled) {
        this.afterMappingWithVarArgsCalled = afterMappingWithVarArgsCalled;
    }

    public boolean isAfterMappingWithArrayCalled() {
        return afterMappingWithArrayCalled;
    }

    public void setAfterMappingWithArrayCalled(boolean afterMappingWithArrayCalled) {
        this.afterMappingWithArrayCalled = afterMappingWithArrayCalled;
    }

    public boolean isAfterMappingContextWithVarArgsAsVarArgsCalled() {
        return afterMappingContextWithVarArgsAsVarArgsCalled;
    }

    public void setAfterMappingContextWithVarArgsAsVarArgsCalled(
        boolean afterMappingContextWithVarArgsAsVarArgsCalled) {
        this.afterMappingContextWithVarArgsAsVarArgsCalled = afterMappingContextWithVarArgsAsVarArgsCalled;
    }

    public boolean isAfterMappingContextWithVarArgsAsArrayCalled() {
        return afterMappingContextWithVarArgsAsArrayCalled;
    }

    public void setAfterMappingContextWithVarArgsAsArrayCalled(boolean afterMappingContextWithVarArgsAsArrayCalled) {
        this.afterMappingContextWithVarArgsAsArrayCalled = afterMappingContextWithVarArgsAsArrayCalled;
    }
}
