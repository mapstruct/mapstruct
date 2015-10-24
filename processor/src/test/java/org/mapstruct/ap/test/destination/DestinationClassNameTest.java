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
package org.mapstruct.ap.test.destination;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.factory.Mappers;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Christophe Labouisse on 27/05/2015.
 */
@RunWith(AnnotationProcessorTestRunner.class)
public class DestinationClassNameTest {
    @Test
    @WithClasses({ DestinationClassNameMapper.class })
    public void shouldGenerateRightName() throws Exception {
        DestinationClassNameMapper instance = DestinationClassNameMapper.INSTANCE;
        assertThat( instance.getClass().getSimpleName() ).isEqualTo( "MyDestinationClassNameMapperCustomImpl" );
    }

    @Test
    @WithClasses({ DestinationClassNameWithJsr330Mapper.class })
    public void shouldNotGenerateSpi() throws Exception {

        Class<DestinationClassNameWithJsr330Mapper> clazz = DestinationClassNameWithJsr330Mapper.class;
        try {
            Mappers.getMapper( clazz );
            Assert.fail( "Should have thrown an ClassNotFoundException" );
        }
        catch ( RuntimeException e ) {
            assertThat( e.getCause() ).isNotNull()
                    .isExactlyInstanceOf( ClassNotFoundException.class )
                    .hasMessage( "Cannot find implementation for " + clazz.getName() );
        }

        DestinationClassNameWithJsr330Mapper instance = (DestinationClassNameWithJsr330Mapper) Class
                .forName( clazz.getName() + "Jsr330Impl" ).newInstance();
        assertThat( instance.getClass().getSimpleName() ).isEqualTo( "DestinationClassNameWithJsr330MapperJsr330Impl" );
    }

    @Test
    @WithClasses({ DestinationClassNameMapperConfig.class, DestinationClassNameMapperWithConfig.class })
    public void shouldGenerateRightNameWithConfig() throws Exception {
        DestinationClassNameMapperWithConfig instance = DestinationClassNameMapperWithConfig.INSTANCE;
        assertThat( instance.getClass().getSimpleName() )
                .isEqualTo( "MyDestinationClassNameMapperWithConfigConfigImpl" );
    }

    @Test
    @WithClasses({ DestinationClassNameMapperConfig.class, DestinationClassNameMapperWithConfigOverride.class })
    public void shouldGenerateRightNameWithConfigOverride() throws Exception {
        DestinationClassNameMapperWithConfigOverride instance = DestinationClassNameMapperWithConfigOverride.INSTANCE;
        assertThat( instance.getClass().getSimpleName() )
                .isEqualTo( "CustomDestinationClassNameMapperWithConfigOverrideMyImpl" );
    }

    @Test
    @WithClasses({ DestinationClassNameMapperDecorated.class, DestinationClassNameMapperDecorator.class })
    public void shouldGenerateRightNameWithDecorator() throws Exception {
        DestinationClassNameMapperDecorated instance = DestinationClassNameMapperDecorated.INSTANCE;
        assertThat( instance.getClass().getSimpleName() )
                .isEqualTo( "MyDestinationClassNameMapperDecoratedCustomImpl" );
        assertThat( instance ).isInstanceOf( DestinationClassNameMapperDecorator.class );
        assertThat( ( (DestinationClassNameMapperDecorator) instance ).delegate.getClass().getSimpleName() )
                .isEqualTo( "MyDestinationClassNameMapperDecoratedCustomImpl_" );
    }

    @Test
    @WithClasses({ AbstractDestinationClassNameMapper.class, AbstractDestinationPackageNameMapper.class })
    public void shouldWorkWithAbstractClasses() {
        AbstractDestinationClassNameMapper mapper1 = AbstractDestinationClassNameMapper.INSTANCE;
        assertThat( mapper1.getClass().getPackage().getName() )
                .isEqualTo( AbstractDestinationClassNameMapper.class.getPackage().getName() );
        assertThat( mapper1.getClass().getSimpleName() ).isEqualTo( "MyAbstractDestinationClassNameMapperCustomImpl" );

        AbstractDestinationPackageNameMapper mapper2 = AbstractDestinationPackageNameMapper.INSTANCE;
        assertThat( mapper2.getClass().getPackage().getName() )
                .isEqualTo( AbstractDestinationPackageNameMapper.class.getPackage().getName() + ".dest" );
        assertThat( mapper2.getClass().getSimpleName() ).isEqualTo( "AbstractDestinationPackageNameMapperImpl" );
    }
}
