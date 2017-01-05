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
package org.mapstruct.ap.test.bugs._913;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Sjaak Derksen
 */
public class DomainWithoutSetter {

    private final Set<String> strings = new HashSet<String>();
    private final Set<Long> longs = new HashSet<Long>();
    private final Set<String> stringsInitialized = new HashSet<String>();
    private final Set<Long> longsInitialized = new HashSet<Long>();
    private final List<String> stringsWithDefault = new ArrayList<String>();

    public Set<String> getStrings() {
        return strings;
    }

    public Set<Long> getLongs() {
        return longs;
    }

    public Set<String> getStringsInitialized() {
        return stringsInitialized;
    }

    public Set<Long> getLongsInitialized() {
        return longsInitialized;
    }

    public List<String> getStringsWithDefault() {
        return stringsWithDefault;
    }
}
