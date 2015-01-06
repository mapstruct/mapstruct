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
package org.mapstruct.ap.test.collection.forged;

import java.util.Map;
import java.util.Set;

public class Source {

    private Set<String> fooSet;
    private Set<String> fooSet2;

    private Map<String, Long> barMap;
    private Map<String, Long> barMap2;

    public Set<String> getFooSet() {
        return fooSet;
    }

    public void setFooSet(Set<String> fooSet) {
        this.fooSet = fooSet;
    }

    public Map<String, Long> getBarMap() {
        return barMap;
    }

    public void setBarMap(Map<String, Long> barMap) {
        this.barMap = barMap;
    }

    public Set<String> getFooSet2() {
        return fooSet2;
    }

    public void setFooSet2( Set<String> fooSet2 ) {
        this.fooSet2 = fooSet2;
    }

    public Map<String, Long> getBarMap2() {
        return barMap2;
    }

    public void setBarMap2( Map<String, Long> barMap2 ) {
        this.barMap2 = barMap2;
    }




}
