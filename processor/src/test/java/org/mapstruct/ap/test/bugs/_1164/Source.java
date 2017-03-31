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
package org.mapstruct.ap.test.bugs._1164;

import java.util.List;
import java.util.Map;

/**
 * @author Filip Hrisafov
 */
class Source {

    public static class SourceNested {
    }

    private List<List<SourceNested>> nestedLists;
    private Map<Integer, List<SourceNested>> nestedMaps;
    private GenericHolder<List<SourceNested>> genericHolder;

    public List<List<SourceNested>> getNestedLists() {
        return nestedLists;
    }

    public void setNestedLists(List<List<SourceNested>> nestedLists) {
        this.nestedLists = nestedLists;
    }

    public Map<Integer, List<SourceNested>> getNestedMaps() {
        return nestedMaps;
    }

    public void setNestedMaps(Map<Integer, List<SourceNested>> nestedMaps) {
        this.nestedMaps = nestedMaps;
    }

    public GenericHolder<List<SourceNested>> getGenericHolder() {
        return genericHolder;
    }

    public void setGenericHolder(GenericHolder<List<SourceNested>> genericHolder) {
        this.genericHolder = genericHolder;
    }
}
