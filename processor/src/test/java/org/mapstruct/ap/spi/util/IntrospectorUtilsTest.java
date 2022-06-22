/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * @author Saheb Preet Singh
 */
public class IntrospectorUtilsTest {

    @Test
    public void testDecapitalize() {
        assertThat( IntrospectorUtils.decapitalize( null ) ).isNull();
        assertThat( IntrospectorUtils.decapitalize( "" ) ).isEqualTo( "" );
        assertThat( IntrospectorUtils.decapitalize( "URL" ) ).isEqualTo( "URL" );
        assertThat( IntrospectorUtils.decapitalize( "FooBar" ) ).isEqualTo( "fooBar" );
        assertThat( IntrospectorUtils.decapitalize( "PArtialCapitalized" ) ).isEqualTo( "PArtialCapitalized" );
        assertThat( IntrospectorUtils.decapitalize( "notCapitalized" ) ).isEqualTo( "notCapitalized" );
        assertThat( IntrospectorUtils.decapitalize( "a" ) ).isEqualTo( "a" );
    }

}
