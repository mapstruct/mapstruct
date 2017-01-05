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
package org.mapstruct.ap.test.bugs._971;

import java.util.Map;

/**
 *
 * @author Filip Hrisafov
 */
public class MapSource {

    private Map<Integer, String> integersSortedMap;
    private Map<Integer, String> integersBaseMap;

    public Map<Integer, String> getIntegersSortedMap() {
        return integersSortedMap;
    }

    public void setIntegersSortedMap(Map<Integer, String> integersSortedMap) {
        this.integersSortedMap = integersSortedMap;
    }

    public Map<Integer, String> getIntegersBaseMap() {
        return integersBaseMap;
    }

    public void setIntegersBaseMap(Map<Integer, String> integersBaseMap) {
        this.integersBaseMap = integersBaseMap;
    }
}
