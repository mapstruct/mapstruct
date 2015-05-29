/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.xml.bind.annotation.XmlElementDecl;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.prism.XmlElementDeclPrism;

/**
 * Selects those methods with matching {@code name} and {@code scope} attributes of the {@link XmlElementDecl}
 * annotation, if that is present. Matching happens in the following order:
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
    public <T extends Method> List<T> getMatchingMethods(Method mappingMethod, List<T> methods,
                                                         Type sourceType, Type targetType,
                                                         SelectionCriteria criteria) {

        // only true source methods are qualifying
        if ( !(mappingMethod instanceof SourceMethod) ) {
            return methods;
        }

        SourceMethod sourceMappingMethod = (SourceMethod) mappingMethod;

        List<T> nameMatches = new ArrayList<T>();
        List<T> scopeMatches = new ArrayList<T>();
        List<T> nameAndScopeMatches = new ArrayList<T>();

        for ( T candidate : methods ) {
            if ( !( candidate instanceof SourceMethod ) ) {
                continue;
            }

            SourceMethod candidateMethod = (SourceMethod) candidate;
            XmlElementDeclPrism xmlElememtDecl = XmlElementDeclPrism.getInstanceOn( candidateMethod.getExecutable() );

            if ( xmlElememtDecl == null ) {
                continue;
            }

            String name = xmlElememtDecl.name();
            TypeMirror scope = xmlElememtDecl.scope();
            TypeMirror target = sourceMappingMethod.getExecutable().getReturnType();

            boolean nameIsSetAndMatches = name != null && name.equals( criteria.getTargetPropertyName() );
            boolean scopeIsSetAndMatches = scope != null && typeUtils.isSameType( scope, target );

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
}
