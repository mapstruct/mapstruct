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
package org.mapstruct.ap.test.componentmodel.spring;

import java.lang.annotation.Annotation;

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
@RunWith(AnnotationProcessorTestRunner.class)
public class SpringMapperTest {
    @Test
    @WithClasses(SpringUnconfiguredMapper.class)
    public void testWithoutSpringConfiguration() throws Exception {
        assertThatMapperHasAnnotationWithValue(
            SpringUnconfiguredMapper.INSTANCE,
            Component.class,
            ""
        );
    }

    @Test
    @WithClasses(SpringMapperComponentModelMapper.class)
    public void testWithDeprecatedMapperComponentTypeValue() throws Exception {
        assertThatMapperHasAnnotationWithValue(
            SpringMapperComponentModelMapper.INSTANCE,
            Component.class,
            ""
        );
    }

    @Test
    @WithClasses(SpringNamedComponentMapper.class)
    public void testAsComponentWithName() throws Exception {
        assertThatMapperHasAnnotationWithValue(
            SpringNamedComponentMapper.INSTANCE,
            Component.class,
            "springMapper"
        );
    }

    @Test
    @WithClasses(SpringNamedServiceMapper.class)
    public void testAsServiceWithName() throws Exception {
        assertThatMapperHasAnnotationWithValue(
            SpringNamedServiceMapper.INSTANCE,
            Service.class,
            "springMapper"
        );
    }

    @Test
    @WithClasses(SpringUnnamedServiceMapper.class)
    public void testAsServiceWithoutName() throws Exception {
        assertThatMapperHasAnnotationWithValue(
            SpringUnnamedServiceMapper.INSTANCE,
            Service.class,
            ""
        );
    }

    @Test
    @WithClasses(SpringNamedDecoratorServiceMapper.class)
    public void testAsDecoratorServiceWithName() throws Exception {
        assertThatMapperHasAnnotationWithValue(
            SpringNamedDecoratorServiceMapper.INSTANCE,
            Service.class,
            "springMapper"
        );
    }

    @Test
    @WithClasses(SpringNamedDecoratorWithOwnQualifierMapper.class)
    public void testAsDecoratorWithOwnQualifierAndName() throws Exception {
        // TODO find a way to check the qualifier name
        assertThatMapperHasAnnotationWithValue(
            SpringNamedDecoratorWithOwnQualifierMapper.INSTANCE,
            Component.class,
            "springMapper"
        );
    }

    private void assertThatMapperHasAnnotationWithValue(Object mapper, Class<? extends Annotation> annotationType,
                                                        String expectedValue) throws Exception {
        assertThat( mapper ).isNotNull();

        Annotation annotation = mapper.getClass().getAnnotation( annotationType );

        assertThat( annotation ).isNotNull();
        assertThat( annotation.getClass().getMethod( "value" ).invoke( annotation ) ).isEqualTo( expectedValue );
    }
}
