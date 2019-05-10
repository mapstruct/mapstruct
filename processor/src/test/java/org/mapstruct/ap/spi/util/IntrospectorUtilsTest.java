/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * @author Saheb Preet Singh
 */
public class IntrospectorUtilsTest {

    @Test
    public void testDecapitalize() throws Exception {
        assertThat( IntrospectorUtils.decapitalize( null ) ).isNull();
        assertThat( IntrospectorUtils.decapitalize( "" ) ).isEqualTo( "" );
        assertThat( IntrospectorUtils.decapitalize( "URL" ) ).isEqualTo( "URL" );
        assertThat( IntrospectorUtils.decapitalize( "FooBar" ) ).isEqualTo( "fooBar" );
        assertThat( IntrospectorUtils.decapitalize( "PArtialCapitalized" ) ).isEqualTo( "PArtialCapitalized" );
        assertThat( IntrospectorUtils.decapitalize( "notCapitalized" ) ).isEqualTo( "notCapitalized" );
        assertThat( IntrospectorUtils.decapitalize( "a" ) ).isEqualTo( "a" );
    }

    @Test
    public void testIsGetter() {
        assertThat( IntrospectorUtils.isGetter( "getA" ) ).isTrue();
        assertThat( IntrospectorUtils.isGetter( "getÜ" ) ).isTrue();
        assertThat( IntrospectorUtils.isGetter( "geta" ) ).isFalse();
        assertThat( IntrospectorUtils.isGetter( "invalidName" ) ).isFalse();
    }

    @Test
    public void testIsBooleanGetter() {
        assertThat( IntrospectorUtils.isBooleanGetter( "isA" ) ).isTrue();
        assertThat( IntrospectorUtils.isBooleanGetter( "isÄ" ) ).isTrue();
        assertThat( IntrospectorUtils.isBooleanGetter( "isa" ) ).isFalse();
        assertThat( IntrospectorUtils.isBooleanGetter( "invalidName" ) ).isFalse();
    }

    @Test
    public void testIsSetter() {
        assertThat( IntrospectorUtils.isSetter( "setA" ) ).isTrue();
        assertThat( IntrospectorUtils.isSetter( "setÖ" ) ).isTrue();
        assertThat( IntrospectorUtils.isSetter( "seta" ) ).isFalse();
        assertThat( IntrospectorUtils.isSetter( "invalidName" ) ).isFalse();
    }

    @Test
    public void testIsWither() {
        assertThat( IntrospectorUtils.isWither( "withA" ) ).isTrue();
        assertThat( IntrospectorUtils.isWither( "withÜ" ) ).isTrue();
        assertThat( IntrospectorUtils.isWither( "witha" ) ).isFalse();
        assertThat( IntrospectorUtils.isWither( "invalidName" ) ).isFalse();
    }

    @Test
    public void testIsAdder() {
        assertThat( IntrospectorUtils.isAdder( "addA" ) ).isTrue();
        assertThat( IntrospectorUtils.isAdder( "addÄ" ) ).isTrue();
        assertThat( IntrospectorUtils.isAdder( "adda" ) ).isFalse();
        assertThat( IntrospectorUtils.isAdder( "invalidName" ) ).isFalse();
    }

    @Test
    public void testIsPresenceChecker() {
        assertThat( IntrospectorUtils.isPresenceChecker( "hasA" ) ).isTrue();
        assertThat( IntrospectorUtils.isPresenceChecker( "hasÖ" ) ).isTrue();
        assertThat( IntrospectorUtils.isPresenceChecker( "hasa" ) ).isFalse();
        assertThat( IntrospectorUtils.isPresenceChecker( "invalidName" ) ).isFalse();
    }
}
