/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._375;


/**
 *
 * @author Sjaak Derksen
 */
public class Source {

    private Int<String> testIterable;

    private Case<String, Integer> testMap;

    public Int<String> getTestIterable() {
        return testIterable;
    }

    public void setTestIterable(Int<String> testIterable) {
        this.testIterable = testIterable;
    }

    public Case<String, Integer> getTestMap() {
        return testMap;
    }

    public void setTestMap(Case<String, Integer> testMap) {
        this.testMap = testMap;
    }

}
