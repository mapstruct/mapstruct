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
package org.mapstruct.ap.test.factories;

/**
 * @author Sjaak Derksen
 */
public class Target {

    private Bar1 prop1;
    private Bar2 prop2;
    private Bar3 prop3;
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
