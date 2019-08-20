/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.beanmapping;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.util.Strings;

import static org.mapstruct.ap.internal.util.Collections.first;
import static org.mapstruct.ap.internal.util.Collections.last;

public abstract class AbstractReference {

    private final Parameter parameter;
    private final List<PropertyEntry> propertyEntries;
    private final boolean isValid;

    protected AbstractReference(Parameter sourceParameter, List<PropertyEntry> sourcePropertyEntries, boolean isValid) {
        this.parameter = sourceParameter;
        this.propertyEntries = sourcePropertyEntries;
        this.isValid = isValid;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public List<PropertyEntry> getPropertyEntries() {
        return propertyEntries;
    }

    public boolean isValid() {
        return isValid;
    }

    public List<String> getElementNames() {
        List<String> elementNames = new ArrayList<>();
        if ( parameter != null ) {
            // only relevant for source properties
            elementNames.add( parameter.getName() );
        }
        for ( PropertyEntry propertyEntry : propertyEntries ) {
            elementNames.add( propertyEntry.getName() );
        }
        return elementNames;
    }

    /**
     * returns the property name on the shallowest nesting level
     * @return
     */
    public PropertyEntry getShallowestProperty() {
        if (  !propertyEntries.isEmpty() ) {
            return first( propertyEntries );
        }
        return null;
    }

    /**
     * returns the property name on the shallowest nesting level
     * @return
     */
    public String getShallowestPropertyName() {
        if (  !propertyEntries.isEmpty() ) {
            return first( propertyEntries ).getName();
        }
        return null;
    }

    /**
     * returns the property name on the deepest nesting level
     * @return
     */
    public PropertyEntry getDeepestProperty() {
        if (  !propertyEntries.isEmpty() ) {
            return last( propertyEntries );
        }
        return null;
    }

    /**
     * returns the property name on the deepest nesting level
     * @return
     */
    public String getDeepestPropertyName() {
        if (  !propertyEntries.isEmpty() ) {
            return last( propertyEntries ).getName();
        }
        return null;
    }

    public boolean isNested() {
        return propertyEntries.size() > 1;
    }

    @Override
    public String toString() {

        String result = "";
        if ( !isValid ) {
            result = "invalid";
        }
        else if ( propertyEntries.isEmpty() ) {
            if ( parameter != null ) {
                result = String.format( "parameter \"%s %s\"", parameter.getType(), parameter.getName() );
            }
        }
        else if ( propertyEntries.size() == 1 ) {
            PropertyEntry propertyEntry = propertyEntries.get( 0 );
            result = String.format( "property \"%s %s\"", propertyEntry.getType(), propertyEntry.getName() );
        }
        else {
            PropertyEntry lastPropertyEntry = propertyEntries.get( propertyEntries.size() - 1 );
            result = String.format(
                "property \"%s %s\"",
                lastPropertyEntry.getType(),
                Strings.join( getElementNames(), "." )
            );
        }
        return result;
    }
}
