/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._375;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Sjaak Derksen
 */
public class Target {

    private List<Integer> testIterable;

    private Map<String, String> testMap;

    public List<Integer> getTestIterable() {
        return testIterable;
    }

    public void setTestIterable(List<Integer> testIterable) {
        this.testIterable = testIterable;
    }

    public Map<String, String> getTestMap() {
        return testMap;
    }

    public void setTestMap(Map<String, String> testMap) {
        this.testMap = testMap;
    }

}
