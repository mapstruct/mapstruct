/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.kotlin.data.jdk17;

import org.junit.jupiter.api.Nested;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithKotlin;
import org.mapstruct.ap.testutil.WithKotlinSources;
import org.mapstruct.ap.testutil.runner.Compiler;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
class KotlinDataJdk17Test {

    @Nested
    @WithKotlinSources(value = "RecordProperty.kt")
    @WithClasses(RecordPropertyMapper.class)
    class Record {

        @ProcessorTest(Compiler.JDK)
        @WithKotlin
        void withKotlin() {
            RecordPropertyMapper.Source source = new RecordPropertyMapper.Source();
            source.setFirstName( "John" );
            source.setLastName( "Doe" );

            RecordProperty property = RecordPropertyMapper.INSTANCE.map( source );
            assertThat( property ).isNotNull();
            assertThat( property.firstName() ).isEqualTo( "John" );
            assertThat( property.lastName() ).isEqualTo( "Doe" );

            source = RecordPropertyMapper.INSTANCE.map( property );
            assertThat( source ).isNotNull();
            assertThat( source.getFirstName() ).isEqualTo( "John" );
            assertThat( source.getLastName() ).isEqualTo( "Doe" );
        }

        @ProcessorTest(Compiler.JDK)
        void withoutKotlin() {
            RecordPropertyMapper.Source source = new RecordPropertyMapper.Source();
            source.setFirstName( "John" );
            source.setLastName( "Doe" );

            RecordProperty property = RecordPropertyMapper.INSTANCE.map( source );
            assertThat( property ).isNotNull();
            assertThat( property.firstName() ).isEqualTo( "John" );
            assertThat( property.lastName() ).isEqualTo( "Doe" );

            source = RecordPropertyMapper.INSTANCE.map( property );
            assertThat( source ).isNotNull();
            assertThat( source.getFirstName() ).isEqualTo( "John" );
            assertThat( source.getLastName() ).isEqualTo( "Doe" );
        }

    }
}
