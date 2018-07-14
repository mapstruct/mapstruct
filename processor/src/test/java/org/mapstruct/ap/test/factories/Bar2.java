/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories;

/**
 * @author Sjaak Derksen
 */
public class Bar2 {
    private String prop;
    private final String someTypeProp;

    public Bar2(String someTypeProp) {
        this.someTypeProp = someTypeProp;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public String getSomeTypeProp() {
        return someTypeProp;
    }

}
