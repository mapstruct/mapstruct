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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
@Mapper
public abstract class SourceTargetMapper {

    public abstract Target map(Source source);

    protected List<List<Target.TargetNested>> mapLists(List<List<Source.SourceNested>> lists) {
        return new ArrayList<List<Target.TargetNested>>();
    }

    protected Map<String, List<Target.MapNested>> map(Map<Integer, List<Source.SourceNested>> map) {
        return new HashMap<String, List<Target.MapNested>>();
    }

    protected GenericHolder<List<Target.GenericNested>> map(GenericHolder<List<Source.SourceNested>> genericHolder) {
        return new GenericHolder<List<Target.GenericNested>>();
    }
}
