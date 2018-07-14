/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories;

/**
 * @author Sjaak Derksen
 */
public class Target {

    private Bar1 prop1;
    private Bar2 prop2;
    private Bar3 prop3;
    private Bar4 prop4;
    private CustomList<String> propList;
    private CustomMap<String, String> propMap;

    public Bar1 getProp1() {
        return prop1;
    }

    public void setProp1(Bar1 prop1) {
        this.prop1 = prop1;
    }

    public Bar2 getProp2() {
        return prop2;
    }

    public void setProp2(Bar2 prop2) {
        this.prop2 = prop2;
    }

    public Bar3 getProp3() {
        return prop3;
    }

    public void setProp3(Bar3 prop3) {
        this.prop3 = prop3;
    }

    public Bar4 getProp4() {
        return prop4;
    }

    public void setProp4(Bar4 prop4) {
        this.prop4 = prop4;
    }

    public CustomList<String> getPropList() {
        return propList;
    }

    public void setPropList(CustomList<String> propList) {
        this.propList = propList;
    }

    public CustomMap<String, String> getPropMap() {
        return propMap;
    }

    public void setPropMap(CustomMap<String, String> propMap) {
        this.propMap = propMap;
    }

}
