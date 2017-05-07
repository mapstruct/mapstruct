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
package org.mapstruct.ap.test.nestedbeans.exclusions;

import java.util.List;

/**
 * @author Filip Hrisafov
 */
class Source {

    static class DeepNestedType {
        //CHECKSTYLE:OFF
        public List<MyType> types;
        //CHECKSTYLE:ON
    }

    static class NestedMyType {
        //CHECKSTYLE:OFF
        public DeepNestedType deepNestedType;
        //CHECKSTYLE:ON
    }

    static class MyType {
        //CHECKSTYLE:OFF
        public String someProperty;
        //CHECKSTYLE:ON
    }

    //CHECKSTYLE:OFF
    public MyType date;
    public MyType calendar;
    public List<MyType> types;
    //TODO Nested error messages do not work yet. I think that this should be solved as part of #1150
    // (or we solve that one first :))
    //public NestedMyType nestedMyType;
    //CHECKSTYLE:ON
}
