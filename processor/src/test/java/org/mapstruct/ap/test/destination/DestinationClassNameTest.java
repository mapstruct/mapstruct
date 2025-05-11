/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.destination;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJavaxInject;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * @author Christophe Labouisse on 27/05/2015.
 */
@WithClasses( Target.class )
public class DestinationClassNameTest {
    @ProcessorTest
    @WithClasses({ DestinationClassNameMapper.class })
    public void shouldGenerateRightName() {
        DestinationClassNameMapper instance = DestinationClassNameMapper.INSTANCE;
        assertThat( instance.getClass().getSimpleName() ).isEqualTo( "MyDestinationClassNameMapperCustomImpl" );
    }

    @ProcessorTest
    @WithClasses({ DestinationClassNameWithJsr330Mapper.class })
    @WithJavaxInject
    public void shouldNotGenerateSpi() throws Exception {

        Class<DestinationClassNameWithJsr330Mapper> clazz = DestinationClassNameWithJsr330Mapper.class;
        try {
            Mappers.getMapper( clazz );
            fail( "Should have thrown an ClassNotFoundException" );
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

    @ProcessorTest
    @WithClasses({ DestinationClassNameMapperConfig.class, DestinationClassNameMapperWithConfig.class })
    public void shouldGenerateRightNameWithConfig() {
        DestinationClassNameMapperWithConfig instance = DestinationClassNameMapperWithConfig.INSTANCE;
        assertThat( instance.getClass().getSimpleName() )
                .isEqualTo( "MyDestinationClassNameMapperWithConfigConfigImpl" );
    }

    @ProcessorTest
    @WithClasses({ DestinationClassNameMapperConfig.class, DestinationClassNameMapperWithConfigOverride.class })
    public void shouldGenerateRightNameWithConfigOverride() {
        DestinationClassNameMapperWithConfigOverride instance = DestinationClassNameMapperWithConfigOverride.INSTANCE;
        assertThat( instance.getClass().getSimpleName() )
                .isEqualTo( "CustomDestinationClassNameMapperWithConfigOverrideMyImpl" );
    }

    @ProcessorTest
    @WithClasses({ DestinationClassNameMapperDecorated.class, DestinationClassNameMapperDecorator.class })
    public void shouldGenerateRightNameWithDecorator() {
        DestinationClassNameMapperDecorated instance = DestinationClassNameMapperDecorated.INSTANCE;
        assertThat( instance.getClass().getSimpleName() )
                .isEqualTo( "MyDestinationClassNameMapperDecoratedCustomImpl" );
        assertThat( instance ).isInstanceOf( DestinationClassNameMapperDecorator.class );
        assertThat( ( (DestinationClassNameMapperDecorator) instance ).delegate.getClass().getSimpleName() )
                .isEqualTo( "MyDestinationClassNameMapperDecoratedCustomImpl_" );
    }

    @ProcessorTest
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
