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
package org.mapstruct.ap.test.conversion.nativetypes;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    ByteTarget sourceToTarget(ByteSource source);

    ByteSource targetToSource(ByteTarget target);

    ByteWrapperTarget sourceToTarget(ByteWrapperSource source);

    ByteWrapperSource targetToSource(ByteWrapperTarget target);

    ShortTarget sourceToTarget(ShortSource source);

    ShortSource targetToSource(ShortTarget target);

    ShortWrapperTarget sourceToTarget(ShortWrapperSource source);

    ShortWrapperSource targetToSource(ShortWrapperTarget target);

    IntTarget sourceToTarget(IntSource source);

    IntSource targetToSource(IntTarget target);

    IntWrapperTarget sourceToTarget(IntWrapperSource source);

    IntWrapperSource targetToSource(IntWrapperTarget target);

    LongTarget sourceToTarget(LongSource source);

    LongSource targetToSource(LongTarget target);

    LongWrapperTarget sourceToTarget(LongWrapperSource source);

    LongWrapperSource targetToSource(LongWrapperTarget target);

    FloatTarget sourceToTarget(FloatSource source);

    FloatSource targetToSource(FloatTarget target);

    FloatWrapperTarget sourceToTarget(FloatWrapperSource source);

    FloatWrapperSource targetToSource(FloatWrapperTarget target);

    DoubleTarget sourceToTarget(DoubleSource source);

    DoubleSource targetToSource(DoubleTarget target);

    DoubleWrapperTarget sourceToTarget(DoubleWrapperSource source);

    DoubleWrapperSource targetToSource(DoubleWrapperTarget target);

}
