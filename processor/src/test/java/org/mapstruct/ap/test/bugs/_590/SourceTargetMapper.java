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
package org.mapstruct.ap.test.bugs._590;

import java.util.logging.XMLFormatter;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.TargetType;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN)
public abstract class SourceTargetMapper {
    public static final SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    public abstract void sourceToTarget(@MappingTarget Target target, Source source);

    public <T extends Target> T unused(String string, @TargetType Class<T> clazz) {
        throw new RuntimeException( "should never be called" );
    }

    public static class Source {
        private String prop;

        public String getProp() {
            return prop;
        }

        public void setProp(String prop) {
            this.prop = prop;
        }
    }

    public static class Target {
        private XMLFormatter prop;

        public XMLFormatter getProp() {
            return prop;
        }

        public void setProp(XMLFormatter prop) {
            this.prop = prop;
        }
    }
}
