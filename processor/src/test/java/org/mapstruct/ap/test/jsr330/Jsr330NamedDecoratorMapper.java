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
package org.mapstruct.ap.test.jsr330;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Jsr330Mapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author Christian Bandowski
 */
@Mapper(componentModel = "jsr330")
@Jsr330Mapper(name = "jsr330Mapper")
@DecoratedWith(Jsr330NamedDecoratorMapper.Jsr330NamedDecoratorMapperDecorator.class)
public interface Jsr330NamedDecoratorMapper {
    Jsr330NamedDecoratorMapper INSTANCE = Mappers.getMapper( Jsr330NamedDecoratorMapper.class );

    String stringToString(String value);

    abstract class Jsr330NamedDecoratorMapperDecorator implements Jsr330NamedDecoratorMapper {
        @Autowired
        @Qualifier("delegate")
        private Jsr330NamedDecoratorMapper delegate;

        @Override
        public String stringToString(String value) {
            return delegate.stringToString( value );
        }
    }
}
