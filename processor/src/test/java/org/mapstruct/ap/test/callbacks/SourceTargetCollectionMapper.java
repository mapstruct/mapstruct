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
package org.mapstruct.ap.test.callbacks;

import java.util.List;
import java.util.Map;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@Mapper( uses = ClassContainingCallbacks.class )
public abstract class SourceTargetCollectionMapper extends BaseMapper {
    public static final SourceTargetCollectionMapper INSTANCE = Mappers.getMapper( SourceTargetCollectionMapper.class );

    public abstract List<Target> sourceToTarget(List<Source> source);

    public abstract void sourceToTarget(List<Source> source, @MappingTarget List<Target> target);

    @IterableMapping(qualifiedBy = Qualified.class)
    public abstract List<Target> qualifiedSourceToTarget(List<Source> source);

    public abstract Map<String, Target> sourceToTarget(Map<String, Source> source);

    public abstract void sourceToTarget(Map<String, Source> source, @MappingTarget Map<String, Target> target);
}
