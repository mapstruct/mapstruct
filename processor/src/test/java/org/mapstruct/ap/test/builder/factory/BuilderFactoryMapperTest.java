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
package org.mapstruct.ap.test.builder.factory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    BuilderFactoryMapper.class,
    BuilderImplicitFactoryMapper.class,
    Person.class,
    PersonDto.class
})
public class BuilderFactoryMapperTest {

    @Test
    public void shouldUseBuilderFactory() {
        Person person = BuilderFactoryMapper.INSTANCE.map( new PersonDto( "Filip" ) );

        assertThat( person.getName() ).isEqualTo( "Filip" );
        assertThat( person.getSource() ).isEqualTo( "Factory with @ObjectFactory" );
    }

    @Test
    public void shouldUseImplicitBuilderFactory() {
        Person person = BuilderImplicitFactoryMapper.INSTANCE.map( new PersonDto( "Filip" ) );

        assertThat( person.getName() ).isEqualTo( "Filip" );
        assertThat( person.getSource() ).isEqualTo( "Implicit Factory" );
    }
}
