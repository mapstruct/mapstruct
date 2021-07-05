/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Strings;

/**
 * Keeps the context where the ForgedMethod is generated, especially handy with nested forged methods
 *
 * @author Dmytro Polovinkin
 */
public class MethodDescription {

    private final MethodDescription prevHistory;
    private final String sourceElement;
    private final String targetPropertyName;
    private final Type targetType;
    private final Type sourceType;
    private final boolean usePropertyNames;
    private String elementType;

    public MethodDescription(MethodDescription history, String sourceElement, String targetPropertyName,
                             Type sourceType, Type targetType, boolean usePropertyNames, String elementType) {
        prevHistory = history;
        this.sourceElement = sourceElement;
        this.targetPropertyName = targetPropertyName;
        this.sourceType = sourceType;
        this.targetType = targetType;
        this.usePropertyNames = usePropertyNames;
        this.elementType = elementType;
    }

    public Type getTargetType() {
        return targetType;
    }

    public Type getSourceType() {
        return sourceType;
    }

    public String createSourcePropertyErrorMessage() {
        return conditionallyCapitalizedElementType() + " \"" + getSourceType().describe() + " " +
            stripBrackets( getDottedSourceElement() ) + "\"";
    }

    /**
     * Capitalization mostly matters to avoid the funny "Can't map map key" message. However it's irrelevant for the
     * "Can't map property" message.
     *
     * @return capitalized or non-capitalized element type
     */
    private String conditionallyCapitalizedElementType() {
        if ( "property".equals( elementType ) ) {
            return elementType;
        }
        else {
            return Strings.capitalize( elementType );
        }
    }

    public String createTargetPropertyName() {
        return stripBrackets( getDottedTargetPropertyName() );
    }

    private String getDottedSourceElement() {
        if ( prevHistory == null ) {
            return sourceElement;
        }
        else {
            if ( usePropertyNames ) {
                return getCorrectDottedPath( prevHistory.getDottedSourceElement(), sourceElement );
            }
            else {
                return prevHistory.getDottedSourceElement();
            }
        }
    }

    private String getDottedTargetPropertyName() {
        if ( prevHistory == null ) {
            return targetPropertyName;
        }
        else {
            if ( usePropertyNames ) {
                return getCorrectDottedPath( prevHistory.getDottedTargetPropertyName(), targetPropertyName );
            }
            else {
                return prevHistory.getDottedTargetPropertyName();
            }

        }
    }

    private String getCorrectDottedPath(String previousPath, String currentProperty) {
        if ( "map key".equals( elementType ) ) {
            return stripBrackets( previousPath ) + "{:key}";
        }
        else if ( "map value".equals( elementType ) ) {
            return stripBrackets( previousPath ) + "{:value}";
        }
        else {
            return previousPath + "." + currentProperty;
        }
    }

    private String stripBrackets(String dottedName) {
        if ( dottedName.endsWith( "[]" ) || dottedName.endsWith( "{}" ) ) {
            dottedName = dottedName.substring( 0, dottedName.length() - 2 );
        }
        return dottedName;
    }
}


