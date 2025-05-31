/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics;

/**
 * @author Ben Zegveld
 */
public class TwinGenericTarget<A, B> {
    private A object1;
    private B object2;

    public A getObject1() {
        return object1;
    }

    public B getObject2() {
        return object2;
    }

    public void setObject1(A object1) {
        this.object1 = object1;
    }

    public void setObject2(B object2) {
        this.object2 = object2;
    }
}
