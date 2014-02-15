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
package org.mapstruct.ap.conversion.methods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;
import javax.xml.bind.JAXBElement;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.TypeFactory;
import org.mapstruct.ap.util.Strings;

/**
 *
 * @author Sjaak Derksen
 */
public class BuildInMethods {

    private final Map<Key, BuildInMethod> conversionMethods = new HashMap<Key, BuildInMethod>();
    private final TypeFactory typeFactory;
    private final Messager messager;

    public BuildInMethods(Messager messager, TypeFactory typeFactory) {
        this.typeFactory = typeFactory;
        this.messager = messager;

        register( JAXBElement.class, Object.class, new JaxbElemToValue( typeFactory ) );

    }

    /**
     * The method looks for a match on equal source type and best matching target type (minimum distance)
     * TODO: investigate whether also the best matching source type should be investigating iso equal.
     *
     * @param sourceType
     * @param targetType
     * @return
     */
    public BuildInMethod getConversion(Type sourceType, Type targetType) {

        List<Key> candidateKeys = new ArrayList<Key>();
        int bestMatchingTargetTypeDistance = Integer.MAX_VALUE;
        for ( Map.Entry<Key, BuildInMethod> entry : conversionMethods.entrySet() ) {

            if ( targetType.isAssignableTo( entry.getKey().targetType )
                    && sourceType.erasure().equals( entry.getKey().sourceType.erasure() ) ) {
                int sourceTypeDistance = targetType.distanceTo( entry.getKey().targetType );
                bestMatchingTargetTypeDistance
                        = addToCandidateListIfMinimal(
                                candidateKeys,
                                bestMatchingTargetTypeDistance,
                                entry.getKey(),
                                sourceTypeDistance );
            }
        }

        if ( candidateKeys.isEmpty() ) {
            return null;
        }

        // print a warning if we find more than one method with minimum source type distance
        if ( candidateKeys.size() > 1 ) {
            List<String> conversionMethodNames = new ArrayList<String>();
            for (Key key : candidateKeys) {
                conversionMethodNames.add( conversionMethods.get( key ).toString() );
            }

            messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(
                    "MapStruct error. Conflicting build in conversion methods %s for sourceType: %s, targetTypes %s.",
                    Strings.join( conversionMethodNames, ", " ),
                    sourceType,
                    targetType )
                );
        }

        return conversionMethods.get( candidateKeys.get( 0) );
    }

    private int addToCandidateListIfMinimal(List<Key> candidatesWithBestMathingType, int bestMatchingTypeDistance,
                                            Key key, int currentTypeDistance) {
        if ( currentTypeDistance == bestMatchingTypeDistance ) {
            candidatesWithBestMathingType.add( key );
        }
        else if ( currentTypeDistance < bestMatchingTypeDistance ) {
            bestMatchingTypeDistance = currentTypeDistance;

            candidatesWithBestMathingType.clear();
            candidatesWithBestMathingType.add( key );
        }
        return bestMatchingTypeDistance;
    }


    private void register(Class<?> sourceType, Class<?> targetType, BuildInMethod conversionMethod) {
        conversionMethods.put( forClasses( sourceType, targetType ), conversionMethod );
    }

    private Key forClasses(Class<?> sourceClass, Class<?> targetClass) {
        Type sourceType = typeFactory.getType( sourceClass );
        Type targetType = typeFactory.getType( targetClass );

        return new Key( sourceType, targetType );
    }

    private static class Key {

        private final Type sourceType;
        private final Type targetType;

        private Key( Type sourceType, Type targetType ) {
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        @Override
        public String toString() {
            return "Key [sourceType=" + sourceType + ", targetType=" + targetType + "]";
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ( ( sourceType == null ) ? 0 : sourceType.hashCode() );
            result = prime * result + ( ( targetType == null ) ? 0 : targetType.hashCode() );
            return result;
        }

        @Override
        public boolean equals( Object obj ) {
            if ( this == obj ) {
                return true;
            }
            if ( obj == null ) {
                return false;
            }
            if ( getClass() != obj.getClass() ) {
                return false;
            }
            Key other = (Key) obj;
            if ( sourceType == null ) {
                if ( other.sourceType != null ) {
                    return false;
                }
            }
            else if ( !sourceType.equals( other.sourceType ) ) {
                return false;
            }
            if ( targetType == null ) {
                if ( other.targetType != null ) {
                    return false;
                }
            }
            else if ( !targetType.equals( other.targetType ) ) {
                return false;
            }
            return true;
        }
    }
}
