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
import java.util.List;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.model.source.SourceMethod;
import org.mapstruct.ap.prism.XmlElementDeclPrism;

/**
 * This class matches XmlElmentDecl annotation. Matching happens in the following order.
 * 1) Name and Scope matches
 * 2) Scope matches
 * 3) Name matches
 * 4) No match at all.
 *
 * If there are Name and Scope matches, only those will be returned, otherwise the next in line (scope matches), etc.
 *
 * @author Sjaak Derksen
 */
public class XmlElementDeclSelector implements MethodSelector {

    private final Types typeUtils;

    public XmlElementDeclSelector(Types typeUtils) {
        this.typeUtils = typeUtils;
    }

    /**
     * {@inheritDoc} {@link MethodSelector}
     */
    @Override
    public <T extends Method> List<T> getMatchingMethods(
        SourceMethod mappingMethod,
        Iterable<T> methods,
        Type parameterType,
        Type returnType,
        String targetPropertyName
    ) {

        List<T> noXmlDeclMatch = new ArrayList<T>();
        List<T> nameMatch = new ArrayList<T>();
        List<T> scopeMatch = new ArrayList<T>();
        List<T> nameAndScopeMatch = new ArrayList<T>();

        for ( T candidate : methods ) {
            if ( candidate instanceof SourceMethod ) {
                SourceMethod candiateMethod = (SourceMethod) candidate;
                XmlElementDeclPrism xmlElememtDecl
                    = XmlElementDeclPrism.getInstanceOn( candiateMethod.getExecutable() );
                if ( xmlElememtDecl != null ) {
                    String name = xmlElememtDecl.name();
                    TypeMirror scope = xmlElememtDecl.scope();
                    TypeMirror target = mappingMethod.getExecutable().getReturnType();
                    if ( ( scope != null ) && ( name != null ) ) {
                        // both scope and name should match when both defined
                        if ( name.equals( targetPropertyName ) && typeUtils.isSameType( scope, target ) ) {
                            nameAndScopeMatch.add( candidate );
                        }
                    }
                    else if ( ( scope == null ) && ( name != null ) ) {
                        // name should match when defined
                        if ( name.equals( targetPropertyName ) ) {
                            nameMatch.add( candidate );
                        }
                    }
                    else if ( ( scope != null ) && ( name == null ) ) {
                        // scope should match when defined
                        if ( typeUtils.isSameType( scope, target ) ) {
                            scopeMatch.add( candidate );
                        }
                    }
                    else {
                        // cannot make verdict based on scope or name, so add
                        noXmlDeclMatch.add( candidate );
                    }
                }
                else {
                    // cannot a verdict on xmldeclannotation, so add
                    noXmlDeclMatch.add( candidate );
                }
            }
            else {
                // cannot a verdict on xmldeclannotation, so add
                noXmlDeclMatch.add( candidate );
            }
        }

        if ( nameAndScopeMatch.size() > 0 ) {
            return nameAndScopeMatch;
        }
        else if ( scopeMatch.size() > 0 ) {
            return scopeMatch;
        }
        else if ( nameMatch.size() > 0 ) {
            return nameMatch;
        }
        return noXmlDeclMatch;
    }
}
