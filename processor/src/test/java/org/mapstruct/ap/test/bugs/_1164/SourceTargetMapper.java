/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
