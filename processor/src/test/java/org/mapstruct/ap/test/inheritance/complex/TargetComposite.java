/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritance.complex;

public class TargetComposite {
    private Reference prop1;
    private Reference prop2;
    private Reference prop3;
    private Number prop4;
    private Iterable<Number> prop5;

    public Reference getProp1() {
        return prop1;
    }

    public void setProp1(Reference prop1) {
        this.prop1 = prop1;
    }

    public Reference getProp2() {
        return prop2;
    }

    public void setProp2(Reference prop2) {
        this.prop2 = prop2;
    }

    public Reference getProp3() {
        return prop3;
    }

    public void setProp3(Reference prop3) {
        this.prop3 = prop3;
    }

    public Number getProp4() {
        return prop4;
    }

    public void setProp4(Number prop4) {
        this.prop4 = prop4;
    }

    public Iterable<Number> getProp5() {
        return prop5;
    }

    public void setProp5(Iterable<Number> prop5) {
        this.prop5 = prop5;
    }
}
