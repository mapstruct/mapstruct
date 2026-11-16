/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.util.TypeUtils;

/**
 * Finds the {@code XmlElementRef} annotation on a field (of the mapping result type or its
 * super types) matching the
 * target property name. Then selects those methods with matching {@code name} and {@code scope} attributes of the
 * {@code XmlElementDecl} annotation, if that is present. Matching happens in the following
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
 *
 * @see JavaxXmlElementDeclSelector
 * @see JakartaXmlElementDeclSelector
 */
abstract class XmlElementDeclSelector implements MethodSelector {

    private final TypeUtils typeUtils;

    XmlElementDeclSelector(TypeUtils typeUtils) {
        this.typeUtils = typeUtils;
    }

    @Override
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(List<SelectedMethod<T>> methods,
                                                                         SelectionContext context) {
        Type resultType = context.getMappingMethod().getResultType();
        String targetPropertyName = context.getSelectionCriteria().getTargetPropertyName();

        List<SelectedMethod<T>> nameMatches = new ArrayList<>();
        List<SelectedMethod<T>> scopeMatches = new ArrayList<>();
        List<SelectedMethod<T>> nameAndScopeMatches = new ArrayList<>();
        XmlElementRefInfo xmlElementRefInfo =
            findXmlElementRef( resultType, targetPropertyName );

        for ( SelectedMethod<T> candidate : methods ) {
            if ( !( candidate.getMethod() instanceof SourceMethod ) ) {
                continue;
            }

            SourceMethod candidateMethod = (SourceMethod) candidate.getMethod();
            XmlElementDeclInfo xmlElementDeclInfo = getXmlElementDeclInfo( candidateMethod.getExecutable() );

            if ( xmlElementDeclInfo == null ) {
                continue;
            }

            String name = xmlElementDeclInfo.nameValue();
            TypeMirror scope = xmlElementDeclInfo.scopeType();

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

        if ( !nameAndScopeMatches.isEmpty() ) {
            return nameAndScopeMatches;
        }
        else if ( !scopeMatches.isEmpty() ) {
            return scopeMatches;
        }
        else if ( !nameMatches.isEmpty() ) {
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
                    XmlElementRefInfo xmlElementRefInfo = getXmlElementRefInfo( enclosed );
                    if ( xmlElementRefInfo != null ) {
                        return new XmlElementRefInfo( xmlElementRefInfo.nameValue(), currentMirror );
                    }
                }
            }
            currentMirror = currentElement.getSuperclass();
            currentElement = (TypeElement) typeUtils.asElement( currentMirror );
        }
        return defaultInfo;
    }

    abstract XmlElementDeclInfo getXmlElementDeclInfo(Element element);

    abstract XmlElementRefInfo getXmlElementRefInfo(Element element);

    static class XmlElementRefInfo {
        private final String nameValue;
        private final TypeMirror sourceType;

        XmlElementRefInfo(String nameValue, TypeMirror sourceType) {
            this.nameValue = nameValue;
            this.sourceType = sourceType;
        }

        String nameValue() {
            return nameValue;
        }

        TypeMirror sourceType() {
            return sourceType;
        }
    }

    /**
     * A class, whose purpose is to combine the use of
     * {@link org.mapstruct.ap.internal.gem.XmlElementDeclGem}
     * and
     * {@link org.mapstruct.ap.internal.gem.jakarta.XmlElementDeclGem}.
     */
    static class XmlElementDeclInfo {

        private final String nameValue;
        private final TypeMirror scopeType;

        XmlElementDeclInfo(String nameValue, TypeMirror scopeType) {
            this.nameValue = nameValue;
            this.scopeType = scopeType;
        }

        String nameValue() {
            return nameValue;
        }

        TypeMirror scopeType() {
            return scopeType;
        }
    }
}
