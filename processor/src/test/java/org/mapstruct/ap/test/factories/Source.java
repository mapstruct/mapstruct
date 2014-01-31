/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.factories;

/**
 * @author Sjaak Derksen
 *
 */
public class Source {

    private Foo1 prop1;
    private Foo2 prop2;
    private Foo3 prop3;

    public Foo1 getProp1() {
        return prop1;
    }

    public void setProp1( Foo1 prop1 ) {
        this.prop1 = prop1;
    }

    public Foo2 getProp2() {
        return prop2;
    }

    public void setProp2( Foo2 prop2 ) {
        this.prop2 = prop2;
    }

    public Foo3 getProp3() {
        return prop3;
    }

    public void setProp3( Foo3 prop3 ) {
        this.prop3 = prop3;
    }
}
