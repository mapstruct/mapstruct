/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor;

import javax.lang.model.SourceVersion;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ankita Advitot
 */
class DefaultVersionInformationTest {

    @Test
    void testSourceVersions() {
        DefaultVersionInformation v8 = new DefaultVersionInformation(
            "1.8.0",
            "vendor",
            "javac",
            SourceVersion.RELEASE_8
        );
        assertThat( v8.isSourceVersionAtLeast9() ).isFalse();
        assertThat( v8.isSourceVersionAtLeast10() ).isFalse();
        assertThat( v8.isSourceVersionAtLeast11() ).isFalse();
        assertThat( v8.isSourceVersionAtLeast14() ).isFalse();
        assertThat( v8.isSourceVersionAtLeast19() ).isFalse();

        DefaultVersionInformation v9 = new DefaultVersionInformation(
            "9.0.0",
            "vendor",
            "javac",
            SourceVersion.RELEASE_9
        );
        assertThat( v9.isSourceVersionAtLeast9() ).isTrue();
        assertThat( v9.isSourceVersionAtLeast10() ).isFalse();
        assertThat( v9.isSourceVersionAtLeast11() ).isFalse();
        assertThat( v9.isSourceVersionAtLeast14() ).isFalse();
        assertThat( v9.isSourceVersionAtLeast19() ).isFalse();

        DefaultVersionInformation v10 = new DefaultVersionInformation(
            "10.0.0",
            "vendor",
            "javac",
            SourceVersion.RELEASE_10
        );
        assertThat( v10.isSourceVersionAtLeast9() ).isTrue();
        assertThat( v10.isSourceVersionAtLeast10() ).isTrue();
        assertThat( v10.isSourceVersionAtLeast11() ).isFalse();
        assertThat( v10.isSourceVersionAtLeast14() ).isFalse();
        assertThat( v10.isSourceVersionAtLeast19() ).isFalse();

        DefaultVersionInformation v11 = new DefaultVersionInformation(
            "11.0.0",
            "vendor",
            "javac",
            SourceVersion.RELEASE_11
        );
        assertThat( v11.isSourceVersionAtLeast9() ).isTrue();
        assertThat( v11.isSourceVersionAtLeast10() ).isTrue();
        assertThat( v11.isSourceVersionAtLeast11() ).isTrue();
        assertThat( v11.isSourceVersionAtLeast14() ).isFalse();
        assertThat( v11.isSourceVersionAtLeast19() ).isFalse();

        DefaultVersionInformation v14 = new DefaultVersionInformation(
            "14.0.0",
            "vendor",
            "javac",
            SourceVersion.RELEASE_14
        );
        assertThat( v14.isSourceVersionAtLeast9() ).isTrue();
        assertThat( v14.isSourceVersionAtLeast10() ).isTrue();
        assertThat( v14.isSourceVersionAtLeast11() ).isTrue();
        assertThat( v14.isSourceVersionAtLeast14() ).isTrue();
        assertThat( v14.isSourceVersionAtLeast19() ).isFalse();

        DefaultVersionInformation v19 = new DefaultVersionInformation(
            "19.0.0",
            "vendor",
            "javac",
            SourceVersion.valueOf( "RELEASE_19" )
        );
        assertThat( v19.isSourceVersionAtLeast9() ).isTrue();
        assertThat( v19.isSourceVersionAtLeast10() ).isTrue();
        assertThat( v19.isSourceVersionAtLeast11() ).isTrue();
        assertThat( v19.isSourceVersionAtLeast14() ).isTrue();
        assertThat( v19.isSourceVersionAtLeast19() ).isTrue();
    }
}
