/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
