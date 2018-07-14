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
package org.mapstruct.ap.test.bugs._1482;

import java.math.BigDecimal;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.TargetType;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class TargetSourceMapper {

    public static final TargetSourceMapper INSTANCE = Mappers.getMapper( TargetSourceMapper.class );

    @Mapping(target = "wrapper", source = "bigDecimal")
    abstract Source2 map(Target target);

    protected <T extends Enum<T>> Enum<T> map(String in, @TargetType  Class<T>clz ) {
        if ( clz.isAssignableFrom( SourceEnum.class )) {
            return (Enum<T>) SourceEnum.valueOf( in );
        }
        return null;
    }

    protected <T> ValueWrapper<T> map(T in) {
        if ( in instanceof BigDecimal ) {
            return (ValueWrapper<T>) new BigDecimalWrapper( (BigDecimal) in );

        }
        return null;
    }
}
