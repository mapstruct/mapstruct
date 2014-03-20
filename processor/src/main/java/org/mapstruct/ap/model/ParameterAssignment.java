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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.mapstruct.ap.model.common.ModelElement;
import org.mapstruct.ap.model.common.Type;

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
public class ParameterAssignment extends ModelElement {



    public static enum AssignmentType { TYPE_CONVERSION, METHOD_REFERENCE, ASSIGNMENT };

    private MethodReference methodReference;
    private TypeConversion typeConversion;
    private final AssignmentType assignmentType;


    public ParameterAssignment() {
        assignmentType = AssignmentType.ASSIGNMENT;
    }

    public ParameterAssignment( MethodReference methodReference ) {
        assignmentType = AssignmentType.METHOD_REFERENCE;
        this.methodReference = methodReference;
    }

    public ParameterAssignment( TypeConversion typeConversion ) {
        assignmentType = AssignmentType.TYPE_CONVERSION;
        this.typeConversion = typeConversion;
    }

    public MethodReference getMethodReference() {
        return methodReference;
    }

    public TypeConversion getTypeConversion() {
        return typeConversion;
    }

    public AssignmentType getAssignmentType() {
        return assignmentType;
    }

    public List<Type> getExceptionTypes() {
        List<Type> exceptionTypes = new ArrayList<Type>();
         switch ( assignmentType ) {
            case METHOD_REFERENCE:
//                exceptionTypes.addAll( methodReference.getExceptionTypes() );
                break;
            case TYPE_CONVERSION:
                exceptionTypes.addAll( typeConversion.getExceptionTypes() );
                break;
            default:
        }
        return exceptionTypes;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> importedTypes = new HashSet<Type>();
        switch ( assignmentType ) {
            case METHOD_REFERENCE:
                importedTypes.addAll( methodReference.getImportTypes() );
                break;
            case TYPE_CONVERSION:
                importedTypes.addAll( typeConversion.getImportTypes() );
                break;
            default:
        }
        return importedTypes;
    }

    @Override
    public String toString() {
        String result = "";
         switch ( assignmentType ) {
            case METHOD_REFERENCE:
                result = methodReference.toString();
                break;
            case TYPE_CONVERSION:
                result = typeConversion.toString();
                break;
            default:
        }
        return result;
    }
}
