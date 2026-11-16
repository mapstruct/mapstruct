/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.destination;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Christophe Labouisse on 27/05/2015.
 */
@IssueKey( "556" )
@WithClasses( Target.class )
public class DestinationPackageNameTest {
    @ProcessorTest
    @WithClasses({ DestinationPackageNameMapper.class })
    public void shouldGenerateInRightPackage() {
        DestinationPackageNameMapper instance = DestinationPackageNameMapper.INSTANCE;
        assertThat( instance.getClass().getName() )
                .isEqualTo( "org.mapstruct.ap.test.destination.dest.DestinationPackageNameMapperImpl" );
    }

    @ProcessorTest
    @WithClasses({ DestinationPackageNameMapperWithSuffix.class })
    public void shouldGenerateInRightPackageWithSuffix() {
        DestinationPackageNameMapperWithSuffix instance = DestinationPackageNameMapperWithSuffix.INSTANCE;
        assertThat( instance.getClass().getName() )
                .isEqualTo( "org.mapstruct.ap.test.destination.dest.DestinationPackageNameMapperWithSuffixMyImpl" );
    }

    @ProcessorTest
    @WithClasses({ DestinationPackageNameMapperConfig.class, DestinationPackageNameMapperWithConfig.class })
    public void shouldGenerateRightSuffixWithConfig() {
        DestinationPackageNameMapperWithConfig instance = DestinationPackageNameMapperWithConfig.INSTANCE;
        assertThat( instance.getClass().getName() )
                .isEqualTo( "org.mapstruct.ap.test.destination.dest.DestinationPackageNameMapperWithConfigImpl" );
    }

    @ProcessorTest
    @WithClasses({ DestinationPackageNameMapperConfig.class, DestinationPackageNameMapperWithConfigOverride.class })
    public void shouldGenerateRightSuffixWithConfigOverride() {
        DestinationPackageNameMapperWithConfigOverride instance =
                DestinationPackageNameMapperWithConfigOverride.INSTANCE;
        assertThat( instance.getClass().getName() )
                .isEqualTo(
                    "org.mapstruct.ap.test.destination.my_dest.DestinationPackageNameMapperWithConfigOverrideImpl"
                );
    }

    @ProcessorTest
    @WithClasses({ DestinationPackageNameMapperDecorated.class, DestinationPackageNameMapperDecorator.class })
    public void shouldGenerateRightSuffixWithDecorator() {
        DestinationPackageNameMapperDecorated instance = DestinationPackageNameMapperDecorated.INSTANCE;
        assertThat( instance.getClass().getName() )
                .isEqualTo( "org.mapstruct.ap.test.destination.dest.DestinationPackageNameMapperDecoratedImpl" );
        assertThat( instance ).isInstanceOf( DestinationPackageNameMapperDecorator.class );
        assertThat( ( (DestinationPackageNameMapperDecorator) instance ).delegate.getClass().getName() )
                .isEqualTo( "org.mapstruct.ap.test.destination.dest.DestinationPackageNameMapperDecoratedImpl_" );
    }
}
