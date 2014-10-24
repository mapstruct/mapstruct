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
package org.mapstruct.ap.model.source.selector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.model.source.SourceMethod;
import org.mapstruct.ap.prism.QualifierPrism;

/**
 * This selector selects a best match based on qualifiers name.
 *
 * @author Sjaak Derksen
 */
public class QualifierSelector implements MethodSelector {

    private final Types typeUtils;


    public QualifierSelector( Types typeUtils, Elements elementUtils ) {
        this.typeUtils = typeUtils;
    }

    @Override
    public <T extends Method> List<T> getMatchingMethods(Method mappingMethod, List<T> methods,
                                                         Type parameterType, Type returnType,
                                                         List<TypeMirror> qualifiers,
                                                         String targetPropertyName) {
        List<T> matches = new ArrayList<T>();

        if ( qualifiers == null || qualifiers.isEmpty() ) {
            return methods;
        }

        for ( T candidate : methods ) {

            if ( !( candidate instanceof SourceMethod ) ) {
                continue;
            }

            // retrieve annotations
            Set<TypeMirror> combinedAnnotations = new HashSet<TypeMirror>();

            // first from the method itself
            SourceMethod candidateSM = (SourceMethod) candidate;
            List<? extends AnnotationMirror> methodAnnotations = candidateSM.getExecutable().getAnnotationMirrors();
            for ( AnnotationMirror methodAnnotation : methodAnnotations ) {
                addOnlyWhenQualifier( combinedAnnotations, methodAnnotation );
            }

            // then from the mapper (if declared)
            Type mapper = candidate.getDeclaringMapper();
            if ( mapper != null ) {
                List<? extends AnnotationMirror> mapperAnnotations = mapper.getTypeElement().getAnnotationMirrors();
                for ( AnnotationMirror mapperAnnotation : mapperAnnotations ) {
                    addOnlyWhenQualifier( combinedAnnotations, mapperAnnotation );
                }
            }

            // now count if all qualifiers are machted
            int matchingQualifierCounter = 0;
            for ( TypeMirror qualifier : qualifiers) {
                for ( TypeMirror annotationType : combinedAnnotations ) {
                    if ( typeUtils.isSameType( qualifier, annotationType ) ) {
                        matchingQualifierCounter++;
                        break;
                    }
                }
            }

            if ( matchingQualifierCounter == qualifiers.size() ) {
                // all qualifiers are matched with a qualifying annotation, add candidate
                matches.add( candidate );
            }
        }
        if ( !matches.isEmpty() ) {
            return matches;
        }
        else {
            return methods;
        }
    }

    private void addOnlyWhenQualifier( Set<TypeMirror> annotationSet, AnnotationMirror candidate ) {
        // only add the candidate annotation when the candidate itself has the annotation 'Qualifier'
        if ( QualifierPrism.getInstanceOn( candidate.getAnnotationType().asElement() ) != null ) {
            annotationSet.add( candidate.getAnnotationType() );
        }
    }
}

