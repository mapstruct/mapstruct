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
package org.mapstruct.ap.test.bugs._1338;

import java.util.ArrayList;

/**
 * @author Filip Hrisafov
 */
public class Target {

    private StringList properties = new StringList();

    public void addProperty(String property) {
        properties.add( property );
    }

    public void setProperties(StringList properties) {
        throw new IllegalStateException( "Setter is there just as a marker it should not be used" );
    }

    public StringList getProperties() {
        return properties;
    }

    public static class StringList extends ArrayList<String> {

        private StringList() {
            // Constructor is private so we get a compile error if we try to instantiate it
        }
    }
}
