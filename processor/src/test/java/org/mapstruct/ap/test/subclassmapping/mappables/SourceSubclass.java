/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.mappables;

public class SourceSubclass extends Source {
    private String subclassProperty;
    private String target5;

    public SourceSubclass(String prop1, String prop2, String prop3, String subProp, String target5) {
        super( prop1, prop2, prop3 );
        setSubclassProperty( subProp );
        setTarget5( target5 );
    }

    public String getSubclassProperty() {
        return subclassProperty;
    }

    public String getTarget5() {
        return target5;
    }

    public void setSubclassProperty(String subclassProperty) {
        this.subclassProperty = subclassProperty;
    }

    public void setTarget5(String target5) {
        this.target5 = target5;
    }
}
