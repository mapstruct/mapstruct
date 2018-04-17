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
package org.mapstruct.ap.test.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Christian Bandowski
 */
@WithClasses({
    SpringUnconfiguredMapper.class,
    SpringNamedComponentMapper.class,
    SpringNamedServiceMapper.class,
    SpringUnnamedServiceMapper.class,
    SpringNamedDecoratorServiceMapper.class,
    SpringNamedDecoratorWithOwnQualifierMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class SpringMapperConfigTest {
    @Test
    public void testWithoutSpringConfiguration() {
        Component annotation = SpringUnconfiguredMapper.INSTANCE.getClass().getAnnotation( Component.class );

        assertThat( annotation ).isNotNull();
        assertThat( annotation.value() ).isEqualTo( "" );
    }

    @Test
    public void testAsComponentWithName() {
        Component annotation = SpringNamedComponentMapper.INSTANCE.getClass().getAnnotation( Component.class );

        assertThat( annotation ).isNotNull();
        assertThat( annotation.value() ).isEqualTo( "springMapper" );
    }

    @Test
    public void testAsServiceWithName() {
        Service annotation = SpringNamedServiceMapper.INSTANCE.getClass().getAnnotation( Service.class );

        assertThat( annotation ).isNotNull();
        assertThat( annotation.value() ).isEqualTo( "springMapper" );
    }

    @Test
    public void testAsServiceWithoutName() {
        Service annotation = SpringUnnamedServiceMapper.INSTANCE.getClass().getAnnotation( Service.class );

        assertThat( annotation ).isNotNull();
        assertThat( annotation.value() ).isEqualTo( "" );
    }

    @Test
    public void testAsDecoratorServiceWithName() {
        Service annotation = SpringNamedDecoratorServiceMapper.INSTANCE.getClass().getAnnotation( Service.class );

        assertThat( annotation ).isNotNull();
        assertThat( annotation.value() ).isEqualTo( "springMapper" );
    }

    @Test
    public void testAsDecoratorWithOwnQualifierAndName() {
        Component annotation = SpringNamedDecoratorWithOwnQualifierMapper.INSTANCE.getClass()
            .getAnnotation( Component.class );

        // TODO find a way to check the qualifier name

        assertThat( annotation ).isNotNull();
        assertThat( annotation.value() ).isEqualTo( "springMapper" );
    }
}
