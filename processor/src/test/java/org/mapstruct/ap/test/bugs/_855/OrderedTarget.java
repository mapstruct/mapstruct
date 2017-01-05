/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
