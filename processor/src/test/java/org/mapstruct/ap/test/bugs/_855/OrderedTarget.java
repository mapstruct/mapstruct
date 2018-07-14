/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._855;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Markus Heberling
 */
class OrderedTarget {
    private List<String> order = new LinkedList<String>();

    public void setField0(String field0) {
        order.add( "field0" );
    }

    public void setField1(String field1) {
        order.add( "field1" );
    }

    public void setField2(String field2) {
        order.add( "field2" );
    }

    public void setField3(String field3) {
        order.add( "field3" );
    }

    public void setField4(String field4) {
        order.add( "field4" );
    }

    public List<String> getOrder() {
        return order;
    }
}
