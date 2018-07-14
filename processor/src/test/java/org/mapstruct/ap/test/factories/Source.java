/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories;

import java.util.List;
import java.util.Map;

/**
 * @author Sjaak Derksen
 */
public class Source {

    private Foo1 prop1;
    private Foo2 prop2;
    private Foo3 prop3;
    private Foo4 prop4;
    private List<String> propList;
    private Map<String, String> propMap;

    public Foo1 getProp1() {
        return prop1;
    }

    public void setProp1(Foo1 prop1) {
        this.prop1 = prop1;
    }

    public Foo2 getProp2() {
        return prop2;
    }

    public void setProp2(Foo2 prop2) {
        this.prop2 = prop2;
    }

    public Foo3 getProp3() {
        return prop3;
    }

    public void setProp3(Foo3 prop3) {
        this.prop3 = prop3;
    }

    public Foo4 getProp4() {
        return prop4;
    }

    public void setProp4(Foo4 prop4) {
        this.prop4 = prop4;
    }

    public List<String> getPropList() {
        return propList;
    }

    public void setPropList(List<String> propList) {
        this.propList = propList;
    }

    public Map<String, String> getPropMap() {
        return propMap;
    }

    public void setPropMap(Map<String, String> propMap) {
        this.propMap = propMap;
    }

}
