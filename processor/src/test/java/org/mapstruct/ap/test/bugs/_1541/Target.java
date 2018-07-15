/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
