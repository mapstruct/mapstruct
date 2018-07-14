/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritance.complex;

import java.util.List;

public class SourceComposite {
    private SourceExt prop1;
    private SourceExt2 prop2;
    private SourceBase prop3;
    private Integer prop4;
    private List<Integer> prop5;

    public SourceExt getProp1() {
        return prop1;
    }

    public void setProp1(SourceExt prop1) {
        this.prop1 = prop1;
    }

    public SourceExt2 getProp2() {
        return prop2;
    }

    public void setProp2(SourceExt2 prop2) {
        this.prop2 = prop2;
    }

    public SourceBase getProp3() {
        return prop3;
    }

    public void setProp3(SourceBase prop3) {
        this.prop3 = prop3;
    }

    public Integer getProp4() {
        return prop4;
    }

    public void setProp4(Integer prop4) {
        this.prop4 = prop4;
    }

    public List<Integer> getProp5() {
        return prop5;
    }

    public void setProp5(List<Integer> prop5) {
        this.prop5 = prop5;
    }
}
