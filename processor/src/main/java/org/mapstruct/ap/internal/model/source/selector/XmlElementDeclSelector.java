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
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.prism.XmlElementDeclPrism;
import org.mapstruct.ap.internal.prism.XmlElementRefPrism;

/**
 * Finds the {@link javax.xml.bind.annotation.XmlElementRef} annotation on a field (of the mapping result type or its
 * super types) matching the
 * target property name. Then selects those methods with matching {@code name} and {@code scope} attributes of the
 * {@link javax.xml.bind.annotation.XmlElementDecl} annotation, if that is present. Matching happens in the following
 * order:
 * <ol>
 * <li>Name and Scope matches</li>
 * <li>Scope matches</li>
 * <li>Name matches</li>
 * </ol>
 * If there are name and scope matches, only those will be returned, otherwise the next in line (scope matches), etc. If
 * the given method is not annotated with {@code XmlElementDecl} it will be considered as matching.
 *
 * @author Sjaak Derksen
 */
public class XmlElementDeclSelector implements MethodSelector {

    private final Types typeUtils;

    public XmlElementDeclSelector(Types typeUtils) {
        this.typeUtils = typeUtils;
    }

    @Override
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(Method mappingMethod,
                                                                          List<SelectedMethod<T>> methods,
                                                                          List<Type> sourceTypes, Type targetType,
                                                                          SelectionCriteria criteria) {

        List<SelectedMethod<T>> nameMatches = new ArrayList<SelectedMethod<T>>();
        List<SelectedMethod<T>> scopeMatches = new ArrayList<SelectedMethod<T>>();
        List<SelectedMethod<T>> nameAndScopeMatches = new ArrayList<SelectedMethod<T>>();
        XmlElementRefInfo xmlElementRefInfo =
            findXmlElementRef( mappingMethod.getResultType(), criteria.getTargetPropertyName() );

        for ( SelectedMethod<T> candidate : methods ) {
            if ( !( candidate.getMethod() instanceof SourceMethod ) ) {
                continue;
            }

            SourceMethod candidateMethod = (SourceMethod) candidate.getMethod();
            XmlElementDeclPrism xmlElememtDecl = XmlElementDeclPrism.getInstanceOn( candidateMethod.getExecutable() );

            if ( xmlElememtDecl == null ) {
                continue;
            }

            String name = xmlElememtDecl.name();
            TypeMirror scope = xmlElememtDecl.scope();

            boolean nameIsSetAndMatches = name != null && name.equals( xmlElementRefInfo.nameValue() );
            boolean scopeIsSetAndMatches =
                scope != null && typeUtils.isSameType( scope, xmlElementRefInfo.sourceType() );

            if ( nameIsSetAndMatches ) {
                if ( scopeIsSetAndMatches ) {
                    nameAndScopeMatches.add( candidate );
                }
                else {
                    nameMatches.add( candidate );
                }
            }
            else if ( scopeIsSetAndMatches ) {
                scopeMatches.add( candidate );
            }
        }

        if ( nameAndScopeMatches.size() > 0 ) {
            return nameAndScopeMatches;
        }
        else if ( scopeMatches.size() > 0 ) {
            return scopeMatches;
        }
        else if ( nameMatches.size() > 0 ) {
            return nameMatches;
        }
        else {
            return methods;
        }
    }

    /**
     * Iterate through resultType and its super types to find a field named targetPropertyName and return information
     * about:
     * <ul>
     * <li>what the value of the name property of the XmlElementRef annotation on that field was</li>
     * <li>on which type the field was found</li>
     * </ul>
     *
     * @param resultType starting point of the iteration
     * @param targetPropertyName name of the field we are looking for
     * @return an XmlElementRefInfo containing the information
     */
    private XmlElementRefInfo findXmlElementRef(Type resultType, String targetPropertyName) {
        TypeMirror startingMirror = resultType.getTypeMirror();
        XmlElementRefInfo defaultInfo = new XmlElementRefInfo( targetPropertyName, startingMirror );
        if ( targetPropertyName == null ) {
            /*
             * sometimes MethodSelectors seem to be called with criteria.getTargetPropertyName() == null so we need to
             * avoid NPEs for that case.
             */
            return defaultInfo;
        }

        TypeMirror currentMirror = startingMirror;
        TypeElement currentElement = resultType.getTypeElement();

        /*
         * Outer loop for resultType and its super types. "currentElement" will be null once we reach Object and try to
         * get a TypeElement for its super type.
         */
        while ( currentElement != null ) {
            /*
             * Inner loop tries to find a field with the targetPropertyName and assumes that where the XmlElementRef is
             * set
             */
            for ( Element enclosed : currentElement.getEnclosedElements() ) {
                if ( enclosed.getKind().equals( ElementKind.FIELD )
                    && enclosed.getSimpleName().contentEquals( targetPropertyName ) ) {
                    XmlElementRefPrism xmlElementRef = XmlElementRefPrism.getInstanceOn( enclosed );
                    if ( xmlElementRef != null ) {
                        return new XmlElementRefInfo( xmlElementRef.name(), currentMirror );
                    }
                }
            }
            currentMirror = currentElement.getSuperclass();
            currentElement = (TypeElement) typeUtils.asElement( currentMirror );
        }
        return defaultInfo;
    }

    private static class XmlElementRefInfo {
        private final String nameValue;
        private final TypeMirror sourceType;

        XmlElementRefInfo(String nameValue, TypeMirror sourceType) {
            this.nameValue = nameValue;
            this.sourceType = sourceType;
        }

        public String nameValue() {
            return nameValue;
        }

        public TypeMirror sourceType() {
            return sourceType;
        }
    }
}
