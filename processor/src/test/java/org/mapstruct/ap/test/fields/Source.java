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
package org.mapstruct.ap.test.fields;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Filip Hrisafov
 */
public class Source {

    // CHECKSTYLE:OFF
    public final int finalInt = 10;
    public int normalInt;
    public final List<Integer> finalList = Arrays.asList( 1, 2, 3 );
    public List<Integer> normalList;
    public Integer fieldOnlyWithGetter;
    // CHECKSTYLE:ON

    private final List<Integer> privateFinalList = new ArrayList<Integer>( Arrays.asList( 3, 4, 5 ) );

    public List<Integer> getPrivateFinalList() {
        return privateFinalList;
    }

    public Integer getFieldOnlyWithGetter() {
        return fieldOnlyWithGetter + 21;
    }
}
