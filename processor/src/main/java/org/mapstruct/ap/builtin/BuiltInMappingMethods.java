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
package org.mapstruct.ap.builtin;

import org.mapstruct.ap.model.BuiltInMappingMethod;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.TypeFactory;
import org.mapstruct.ap.util.Strings;

/**
 *
 * @author Sjaak Derksen
 */
public class BuiltInMappingMethods {

    private final Set<BuiltInMappingMethod> builtInMethods = new HashSet<BuiltInMappingMethod>();
    private final Messager messager;

    public BuiltInMappingMethods( Messager messager, TypeFactory typeFactory ) {
        this.messager = messager;

        builtInMethods.add( new JaxbElemToValue( typeFactory ) );
        builtInMethods.add( new ListOfJaxbElemToListOfValue( typeFactory ) );
        builtInMethods.add( new DateToXmlGregorianCalendar( typeFactory ) );
        builtInMethods.add( new XmlGregorianCalendarToDate( typeFactory ) );
        builtInMethods.add( new StringToXmlGregorianCalendar( typeFactory ) );
        builtInMethods.add( new XmlGregorianCalendarToString( typeFactory ) );
        builtInMethods.add( new CalendarToXmlGregorianCalendar( typeFactory ) );
        builtInMethods.add( new XmlGregorianCalendarToCalendar( typeFactory ) );
    }

    /**
     * The method looks for a match on equal source type and best matching target type (minimum distance) TODO:
     * investigate whether also the best matching source type should be investigating iso equal.
     *
     * @param sourceType
     * @param targetType
     * @return
     */
    public BuiltInMappingMethod getConversion( Type sourceType, Type targetType ) {

        List<BuiltInMappingMethod> candidates = new ArrayList<BuiltInMappingMethod>();
        int bestMatchingTargetTypeDistance = Integer.MAX_VALUE;
        for ( BuiltInMappingMethod entry : builtInMethods ) {

            if ( targetType.erasure().isAssignableTo( entry.target() )
                    && sourceType.erasure().isAssignableTo( entry.source() ) ) {

                if ( entry.doGenericsMatch( sourceType, targetType ) ) {
                    int sourceTypeDistance = targetType.distanceTo( entry.target() );
                    bestMatchingTargetTypeDistance
                            = addToCandidateListIfMinimal(
                                    candidates,
                                    bestMatchingTargetTypeDistance,
                                    entry,
                                    sourceTypeDistance );
                }
            }
        }

        if ( candidates.isEmpty() ) {
            return null;
        }

        if ( candidates.size() > 1 ) {
            // print a warning if we find more than one method with minimum source type distance
            List<String> builtInMethodNames = new ArrayList<String>();
            for ( BuiltInMappingMethod candidate : candidates ) {
                builtInMethodNames.add( candidate.getName() );
            }

            messager.printMessage(
                    Diagnostic.Kind.ERROR,
                    String.format(
                        "MapStruct error. Conflicting build-in methods %s for sourceType: %s, targetTypes %s.",
                        Strings.join( builtInMethodNames, ", " ),
                        sourceType,
                        targetType )
            );
        }

        return candidates.get( 0 );
    }

    private int addToCandidateListIfMinimal( List<BuiltInMappingMethod>  candidatesWithBestMathingType,
            int bestMatchingTypeDistance, BuiltInMappingMethod builtInMethod, int currentTypeDistance ) {
        if ( currentTypeDistance == bestMatchingTypeDistance ) {
            candidatesWithBestMathingType.add( builtInMethod );
        }
        else if ( currentTypeDistance < bestMatchingTypeDistance ) {
            bestMatchingTypeDistance = currentTypeDistance;

            candidatesWithBestMathingType.clear();
            candidatesWithBestMathingType.add( builtInMethod );
        }
        return bestMatchingTypeDistance;
    }
}
