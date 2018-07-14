/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.references;

/**
 * @author Andreas Gudian
 *
 */
public class SourceWithWrappers {
    private SomeType prop1;
    private SomeType prop2;
    private SomeType prop3;
    private GenericWrapper<SomeType> prop4;

    public SomeType getProp1() {
        return prop1;
    }

    public void setProp1(SomeType prop1) {
        this.prop1 = prop1;
    }

    public SomeType getProp2() {
        return prop2;
    }

    public void setProp2(SomeType prop2) {
        this.prop2 = prop2;
    }

    public SomeType getProp3() {
        return prop3;
    }

    public void setProp3(SomeType prop3) {
        this.prop3 = prop3;
    }

    public GenericWrapper<SomeType> getProp4() {
        return prop4;
    }

    public void setProp4(GenericWrapper<SomeType> prop4) {
        this.prop4 = prop4;
    }
}
