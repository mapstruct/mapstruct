/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.type.TypeKind;

import org.mapstruct.ap.internal.model.common.Parameter;

public class ParameterAssignmentUtil {

    private ParameterAssignmentUtil() {
    }

    public static List<Parameter> getParameterAssignments(List<Parameter> availableParams,
                                                          List<Parameter> methodParameters) {
        List<Parameter> result = new ArrayList<Parameter>( methodParameters.size() );

        for ( Parameter methodParam : methodParameters ) {
            List<Parameter> assignableParams = findCandidateParameters( availableParams, methodParam );

            if ( assignableParams.isEmpty() ) {
                return null;
            }

            if ( assignableParams.size() == 1 ) {
                result.add( assignableParams.get( 0 ) );
            }
            else if ( assignableParams.size() > 1 ) {
                Parameter paramWithMatchingName = findParameterWithName( assignableParams, methodParam.getName() );

                if ( paramWithMatchingName != null ) {
                    result.add( paramWithMatchingName );
                }
                else {
                    return null;
                }
            }
        }

        return result;
    }

    private static Parameter findParameterWithName(List<Parameter> parameters, String name) {
        for ( Parameter param : parameters ) {
            if ( name.equals( param.getName() ) ) {
                return param;
            }
        }

        return null;
    }

    /**
     * @param candidateParameters available for assignment.
     * @param parameter that need assignment from one of the candidate parameters.
     * @return list of matching candidate parameters that can be assigned.
     */
    private static List<Parameter> findCandidateParameters(List<Parameter> candidateParameters, Parameter parameter) {
        List<Parameter> result = new ArrayList<Parameter>( candidateParameters.size() );

        for ( Parameter candidate : candidateParameters ) {
            if ( ( isTypeVarOrWildcard( parameter ) || candidate.getType().isAssignableTo( parameter.getType() ) )
                && parameter.isMappingTarget() == candidate.isMappingTarget() && !parameter.isTargetType()
                && !candidate.isTargetType() ) {
                result.add( candidate );
            }
            else if ( parameter.isTargetType() && candidate.isTargetType() ) {
                result.add( candidate );
            }
        }

        return result;
    }

    private static boolean isTypeVarOrWildcard(Parameter parameter) {
        TypeKind kind = parameter.getType().getTypeMirror().getKind();
        return kind == TypeKind.TYPEVAR || kind == TypeKind.WILDCARD;
    }
}
