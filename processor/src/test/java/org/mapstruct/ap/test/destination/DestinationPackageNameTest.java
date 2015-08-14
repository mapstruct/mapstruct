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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

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
