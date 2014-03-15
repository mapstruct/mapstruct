/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.model;

/**
 * This class carries the possible ways to do an assignment to a parameter on a mapping target
 *
 * The following options exist:
 * <ol>
 * <li>MethodReference</li>
 * <li>TypeConversion</li>
 * <li>Simple Assignment (empty ParameterAssignment)</li>
 * </ol>
 *
 * @author Sjaak Derksen
 */
public class ParameterAssignment {


    private MethodReference methodReference;
    private TypeConversion typeConversion;

    public ParameterAssignment() {
    }

    public ParameterAssignment( MethodReference methodReference ) {
        this.methodReference = methodReference;
    }

    public ParameterAssignment( TypeConversion typeConversion ) {
        this.typeConversion = typeConversion;
    }

    public MethodReference getMethodReference() {
        return methodReference;
    }

    public TypeConversion getTypeConversion() {
        return typeConversion;
    }

}
