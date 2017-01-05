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
package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.prism.NamedPrism;
import org.mapstruct.ap.internal.prism.QualifierPrism;

/**
 * This selector selects a best match based on qualifier annotations.
 * <p>
 * A method is said to be marked with a qualifier annotation if the class in which it resides is annotated with a
 * qualifier annotation or if the method itself is annotated with a qualifier annotation or both.
 * <p>
 * Rules:
 * <ol>
 * <li>If no qualifiers are requested in the selection criteria, then only candidate methods without any qualifier
 * annotations remain in the list of potential candidates</li>
 * <li>If multiple qualifiers (qualifedBy) are specified, then all of them need to be present at a candidate for it to
 * match.</li>
 * <li>If no candidate matches the required qualifiers, then all candidates are returned.</li>
 * </ol>
 *
 * @author Sjaak Derksen
 */
public class QualifierSelector implements MethodSelector {

    private final Types typeUtils;
    private final TypeMirror namedAnnotationTypeMirror;

    public QualifierSelector( Types typeUtils, Elements elementUtils ) {
        this.typeUtils = typeUtils;
        namedAnnotationTypeMirror = elementUtils.getTypeElement( "org.mapstruct.Named" ).asType();
    }

    @Override
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(Method mappingMethod,
                                                                          List<SelectedMethod<T>> methods,
                                                                          List<Type> sourceTypes, Type targetType,
                                                                          SelectionCriteria criteria) {

        int numberOfQualifiersToMatch = 0;

        // Define some local collections and make sure that they are defined.
        List<TypeMirror> qualifierTypes = new ArrayList<TypeMirror>();
        if ( criteria.getQualifiers() != null ) {
            qualifierTypes.addAll( criteria.getQualifiers() );
            numberOfQualifiersToMatch += criteria.getQualifiers().size();
        }
        List<String> qualfiedByNames = new ArrayList<String>();
        if ( criteria.getQualifiedByNames() != null ) {
            qualfiedByNames.addAll( criteria.getQualifiedByNames() );
            numberOfQualifiersToMatch += criteria.getQualifiedByNames().size();
        }

        // add the mapstruct @Named annotation as annotation to look for
        if ( !qualfiedByNames.isEmpty() ) {
            qualifierTypes.add( namedAnnotationTypeMirror );
        }

        // Check there are qualfiers for this mapping: Mapping#qualifier or Mapping#qualfiedByName
        if ( qualifierTypes.isEmpty() ) {
            // When no qualifiers, disqualify all methods marked with a qualifier by removing them from the candidates
            List<SelectedMethod<T>> nonQualiferAnnotatedMethods = new ArrayList<SelectedMethod<T>>( methods.size() );
            for ( SelectedMethod<T> candidate : methods ) {

                if ( candidate.getMethod() instanceof SourceMethod ) {
                    Set<AnnotationMirror> qualifierAnnotations = getQualifierAnnotationMirrors( candidate.getMethod() );
                    if ( qualifierAnnotations.isEmpty() ) {
                        nonQualiferAnnotatedMethods.add( candidate );
                    }
                }
                else {
                    nonQualiferAnnotatedMethods.add( candidate );
                }

            }
            return nonQualiferAnnotatedMethods;
        }
        else {
            // Check all methods marked with qualfier (or methods in Mappers marked wiht a qualfier) for matches.
            List<SelectedMethod<T>> matches = new ArrayList<SelectedMethod<T>>( methods.size() );
            for ( SelectedMethod<T> candidate : methods ) {

                if ( !( candidate.getMethod() instanceof SourceMethod ) ) {
                    continue;
                }

                // retrieve annotations
                Set<AnnotationMirror> qualifierAnnotationMirrors =
                    getQualifierAnnotationMirrors( candidate.getMethod() );

                // now count if all qualifiers are matched
                int matchingQualifierCounter = 0;
                    for ( AnnotationMirror qualifierAnnotationMirror : qualifierAnnotationMirrors ) {
                for ( TypeMirror qualifierType : qualifierTypes ) {

                        // get the type of the annotation mirror.
                        DeclaredType qualifierAnnotationType = qualifierAnnotationMirror.getAnnotationType();
                        if ( typeUtils.isSameType( qualifierType, qualifierAnnotationType ) ) {
                            // Match! we have an annotation which has the @Qualifer marker ( could be @Named as well )
                            if ( typeUtils.isSameType( qualifierAnnotationType, namedAnnotationTypeMirror ) ) {
                                // Match! its an @Named, so do the additional check on name.
                                NamedPrism namedPrism = NamedPrism.getInstance( qualifierAnnotationMirror );
                                if ( namedPrism.value() != null && qualfiedByNames.contains( namedPrism.value() ) ) {
                                    // Match! its an @Name and the value matches as well. Oh boy.
                                    matchingQualifierCounter++;
                                }
                            }
                            else {
                                // Match! its a self declared qualifer annoation (marked with @Qualifier)
                                matchingQualifierCounter++;
                            }
                            break;
                        }

                    }
                }

                if ( matchingQualifierCounter == numberOfQualifiersToMatch ) {
                    // Only if all qualifiers are matched with a qualifying annotation, add candidate
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
    }

    private Set<AnnotationMirror> getQualifierAnnotationMirrors( Method candidate ) {

        // retrieve annotations
        Set<AnnotationMirror> qualiferAnnotations = new HashSet<AnnotationMirror>();

        // first from the method itself
        SourceMethod candidateSM = (SourceMethod) candidate;
        List<? extends AnnotationMirror> methodAnnotations = candidateSM.getExecutable().getAnnotationMirrors();
        for ( AnnotationMirror methodAnnotation : methodAnnotations ) {
            addOnlyWhenQualifier( qualiferAnnotations, methodAnnotation );
        }

        // then from the mapper (if declared)
        Type mapper = candidate.getDeclaringMapper();
        if ( mapper != null ) {
            List<? extends AnnotationMirror> mapperAnnotations = mapper.getTypeElement().getAnnotationMirrors();
            for ( AnnotationMirror mapperAnnotation : mapperAnnotations ) {
                addOnlyWhenQualifier( qualiferAnnotations, mapperAnnotation );
            }
        }

        return qualiferAnnotations;
    }

    private void addOnlyWhenQualifier( Set<AnnotationMirror> annotationSet, AnnotationMirror candidate ) {
        // only add the candidate annotation when the candidate itself has the annotation 'Qualifier'
        if ( QualifierPrism.getInstanceOn( candidate.getAnnotationType().asElement() ) != null ) {
            annotationSet.add( candidate );
        }
    }

}
