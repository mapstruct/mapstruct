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
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Sjaak Derksen
 */
public class DtoWithPresenceCheck {

    private List<String> strings;
    private List<String> stringsInitialized = new ArrayList<String>( Arrays.asList( "5" ) );
    private List<String> stringsWithDefault;

    public boolean hasStrings() {
        return false;
    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public boolean hasStringsInitialized() {
        return true;
    }

    public List<String> getStringsInitialized() {
        return stringsInitialized;
    }

    public void setStringsInitialized(List<String> stringsInitialized) {
        this.stringsInitialized = stringsInitialized;
    }

    public boolean hasStringsWithDefault() {
        return false;
    }

    public List<String> getStringsWithDefault() {
        return stringsWithDefault;
    }

    public void setStringsWithDefault(List<String> stringsWithDefault) {
        this.stringsWithDefault = stringsWithDefault;
    }

}
