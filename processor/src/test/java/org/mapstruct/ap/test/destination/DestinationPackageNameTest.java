/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.destination;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Christophe Labouisse on 27/05/2015.
 */
@IssueKey( "556" )
@RunWith(AnnotationProcessorTestRunner.class)
public class DestinationPackageNameTest {
    @Test
    @WithClasses({ DestinationPackageNameMapper.class })
    public void shouldGenerateInRightPackage() throws Exception {
        DestinationPackageNameMapper instance = DestinationPackageNameMapper.INSTANCE;
        assertThat( instance.getClass().getName() )
                .isEqualTo( "org.mapstruct.ap.test.destination.dest.DestinationPackageNameMapperImpl" );
    }

    @Test
    @WithClasses({ DestinationPackageNameMapperWithSuffix.class })
    public void shouldGenerateInRightPackageWithSuffix() throws Exception {
        DestinationPackageNameMapperWithSuffix instance = DestinationPackageNameMapperWithSuffix.INSTANCE;
        assertThat( instance.getClass().getName() )
                .isEqualTo( "org.mapstruct.ap.test.destination.dest.DestinationPackageNameMapperWithSuffixMyImpl" );
    }

    @Test
    @WithClasses({ DestinationPackageNameMapperConfig.class, DestinationPackageNameMapperWithConfig.class })
    public void shouldGenerateRightSuffixWithConfig() throws Exception {
        DestinationPackageNameMapperWithConfig instance = DestinationPackageNameMapperWithConfig.INSTANCE;
        assertThat( instance.getClass().getName() )
                .isEqualTo( "org.mapstruct.ap.test.destination.dest.DestinationPackageNameMapperWithConfigImpl" );
    }

    @Test
    @WithClasses({ DestinationPackageNameMapperConfig.class, DestinationPackageNameMapperWithConfigOverride.class })
    public void shouldGenerateRightSuffixWithConfigOverride() throws Exception {
        DestinationPackageNameMapperWithConfigOverride instance =
                DestinationPackageNameMapperWithConfigOverride.INSTANCE;
        assertThat( instance.getClass().getName() )
                .isEqualTo(
                    "org.mapstruct.ap.test.destination.my_dest.DestinationPackageNameMapperWithConfigOverrideImpl"
                );
    }

    @Test
    @WithClasses({ DestinationPackageNameMapperDecorated.class, DestinationPackageNameMapperDecorator.class })
    public void shouldGenerateRightSuffixWithDecorator() throws Exception {
        DestinationPackageNameMapperDecorated instance = DestinationPackageNameMapperDecorated.INSTANCE;
        assertThat( instance.getClass().getName() )
                .isEqualTo( "org.mapstruct.ap.test.destination.dest.DestinationPackageNameMapperDecoratedImpl" );
        assertThat( instance ).isInstanceOf( DestinationPackageNameMapperDecorator.class );
        assertThat( ( (DestinationPackageNameMapperDecorator) instance ).delegate.getClass().getName() )
                .isEqualTo( "org.mapstruct.ap.test.destination.dest.DestinationPackageNameMapperDecoratedImpl_" );
    }
}
