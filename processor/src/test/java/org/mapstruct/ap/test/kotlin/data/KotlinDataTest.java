/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.kotlin.data;

import org.junit.jupiter.api.Nested;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithKotlin;
import org.mapstruct.ap.testutil.WithKotlinSources;
import org.mapstruct.ap.testutil.WithTestDependency;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
class KotlinDataTest {

    @Nested
    @WithClasses({
        CustomerEntity.class,
        CustomerMapper.class
    })
    @WithKotlinSources("CustomerDto.kt")
    class Standard {

        @ProcessorTest
        void shouldMapData() {
            CustomerEntity customer = CustomerMapper.INSTANCE.fromData( new CustomerDto(
                "Kermit",
                "kermit@test.com"
            ) );

            assertThat( customer ).isNotNull();
            assertThat( customer.getName() ).isEqualTo( "Kermit" );
            assertThat( customer.getMail() ).isEqualTo( "kermit@test.com" );
        }

        @ProcessorTest
        void shouldMapIntoData() {
            CustomerEntity entity = new CustomerEntity();
            entity.setName( "Kermit" );
            entity.setMail( "kermit@test.com" );

            CustomerDto customer = CustomerMapper.INSTANCE.toData( entity );

            assertThat( customer ).isNotNull();
            assertThat( customer.getName() ).isEqualTo( "Kermit" );
            assertThat( customer.getEmail() ).isEqualTo( "kermit@test.com" );
        }

    }

    @Nested
    @WithClasses({
        SinglePropertyMapper.class,
    })
    @WithKotlinSources("SingleProperty.kt")
    class SingleData {

        @ProcessorTest
        @WithKotlin
        void shouldCompileWithoutWarnings() {

            SingleProperty property = SinglePropertyMapper.INSTANCE.map( new SinglePropertyMapper.Source( "test" ) );
            assertThat( property ).isNotNull();
            assertThat( property.getValue() ).isEqualTo( "test" );
        }

        @ProcessorTest
        @ExpectedCompilationOutcome(
            value = CompilationResult.SUCCEEDED,
            diagnostics = {
                @Diagnostic(
                    kind = javax.tools.Diagnostic.Kind.WARNING,
                    type = SinglePropertyMapper.class,
                    line = 19,
                    message = "Unmapped target property: \"copy\"."
                )
            }
        )
        void shouldCompileWithWarnings() {

            SingleProperty property = SinglePropertyMapper.INSTANCE.map( new SinglePropertyMapper.Source( "test" ) );
            assertThat( property ).isNotNull();
            assertThat( property.getValue() ).isEqualTo( "test" );
        }
    }

    @Nested
    @WithClasses({
        AllDefaultsPropertyMapper.class,
    })
    @WithKotlinSources("AllDefaultsProperty.kt")
    class AllDefaults {

        @ProcessorTest
        @WithKotlin
        void shouldCompileWithoutWarnings() {

            AllDefaultsProperty property = AllDefaultsPropertyMapper.INSTANCE.map( new AllDefaultsPropertyMapper.Source(
                "Kermit",
                "the Frog"
            ) );
            assertThat( property ).isNotNull();
            assertThat( property.getFirstName() ).isEqualTo( "Kermit" );
            assertThat( property.getLastName() ).isEqualTo( "the Frog" );
        }

        @ProcessorTest
        @ExpectedCompilationOutcome(
            value = CompilationResult.SUCCEEDED,
            diagnostics = {
                @Diagnostic(
                    kind = javax.tools.Diagnostic.Kind.WARNING,
                    type = AllDefaultsPropertyMapper.class,
                    line = 19,
                    message = "No target property found for target \"AllDefaultsProperty\"."
                )
            }
        )
        void shouldCompileWithWarnings() {

            AllDefaultsProperty property = AllDefaultsPropertyMapper.INSTANCE.map( new AllDefaultsPropertyMapper.Source(
                "Kermit",
                "the Frog"
            ) );
            assertThat( property ).isNotNull();
            assertThat( property.getFirstName() ).isEqualTo( "Kermit" );
            assertThat( property.getLastName() ).isEqualTo( "the Frog" );
        }
    }

    @Nested
    @WithClasses({
        MultiConstructorPropertyMapper.class,
    })
    @WithKotlinSources("MultiConstructorProperty.kt")
    class MultiConstructor {

        @ProcessorTest
        @WithKotlin
        void shouldCompileWithoutWarnings() {

            MultiConstructorProperty property = MultiConstructorPropertyMapper.INSTANCE
                .map( new MultiConstructorPropertyMapper.Source(
                    "Kermit",
                    "the Frog",
                    "Kermit the Frog"
                ) );
            assertThat( property ).isNotNull();
            assertThat( property.getFirstName() ).isEqualTo( "Kermit" );
            assertThat( property.getLastName() ).isEqualTo( "the Frog" );
            assertThat( property.getDisplayName() ).isEqualTo( "Kermit the Frog" );
        }

        @ProcessorTest
        @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                @Diagnostic(
                    kind = javax.tools.Diagnostic.Kind.ERROR,
                    type = MultiConstructorPropertyMapper.class,
                    line = 19,
                    messageRegExp = "Ambiguous constructors found for creating .*MultiConstructorProperty: " +
                        "MultiConstructorProperty\\(java.lang.String, java.lang.String.*\\), " +
                        "MultiConstructorProperty\\(java.lang.String, java.lang.String.*\\)\\. " +
                        "Either declare parameterless constructor or annotate the default constructor with an " +
                        "annotation named @Default\\."
                )
            }
        )
        void shouldFailToCompile() {

        }
    }

    @Nested
    @WithClasses({
        MultiSimilarConstructorPropertyMapper.class,
    })
    @WithKotlinSources("MultiSimilarConstructorProperty.kt")
    class MultiSimilarConstructor {

        @ProcessorTest
        @WithKotlin
        void shouldCompileWithoutWarnings() {
            PrimaryString primaryString = MultiSimilarConstructorPropertyMapper.INSTANCE.map(
                new MultiSimilarConstructorPropertyMapper.Source(
                    "Kermit",
                    "the Frog"
                ),
                "Kermit the Frog"
            );
            assertThat( primaryString ).isNotNull();
            assertThat( primaryString.getFirstName() ).isEqualTo( "Kermit" );
            assertThat( primaryString.getLastName() ).isEqualTo( "the Frog" );
            assertThat( primaryString.getDisplayName() ).isEqualTo( "Kermit the Frog" );

            PrimaryInt primaryInt = MultiSimilarConstructorPropertyMapper.INSTANCE.map(
                new MultiSimilarConstructorPropertyMapper.Source(
                    "Kermit",
                    "the Frog"
                ),
                42
            );
            assertThat( primaryInt ).isNotNull();
            assertThat( primaryInt.getFirstName() ).isEqualTo( "Kermit" );
            assertThat( primaryInt.getLastName() ).isEqualTo( "the Frog" );
            assertThat( primaryInt.getAge() ).isEqualTo( 42 );

            PrimaryLong primaryLong = MultiSimilarConstructorPropertyMapper.INSTANCE.map(
                new MultiSimilarConstructorPropertyMapper.Source(
                    "Kermit",
                    "the Frog"
                ),
                42L
            );
            assertThat( primaryLong ).isNotNull();
            assertThat( primaryLong.getFirstName() ).isEqualTo( "Kermit" );
            assertThat( primaryLong.getLastName() ).isEqualTo( "the Frog" );
            assertThat( primaryLong.getAge() ).isEqualTo( 42 );

            PrimaryBoolean primaryBoolean = MultiSimilarConstructorPropertyMapper.INSTANCE.map(
                new MultiSimilarConstructorPropertyMapper.Source(
                    "Kermit",
                    "the Frog"
                ),
                true
            );
            assertThat( primaryBoolean ).isNotNull();
            assertThat( primaryBoolean.getFirstName() ).isEqualTo( "Kermit" );
            assertThat( primaryBoolean.getLastName() ).isEqualTo( "the Frog" );
            assertThat( primaryBoolean.getActive() ).isTrue();

            PrimaryByte primaryByte = MultiSimilarConstructorPropertyMapper.INSTANCE.map(
                new MultiSimilarConstructorPropertyMapper.Source(
                    "Kermit",
                    "the Frog"
                ),
                (byte) 4
            );
            assertThat( primaryByte ).isNotNull();
            assertThat( primaryByte.getFirstName() ).isEqualTo( "Kermit" );
            assertThat( primaryByte.getLastName() ).isEqualTo( "the Frog" );
            assertThat( primaryByte.getB() ).isEqualTo( (byte) 4 );

            PrimaryShort primaryShort = MultiSimilarConstructorPropertyMapper.INSTANCE.map(
                new MultiSimilarConstructorPropertyMapper.Source(
                    "Kermit",
                    "the Frog"
                ),
                (short) 4
            );
            assertThat( primaryShort ).isNotNull();
            assertThat( primaryShort.getFirstName() ).isEqualTo( "Kermit" );
            assertThat( primaryShort.getLastName() ).isEqualTo( "the Frog" );
            assertThat( primaryShort.getAge() ).isEqualTo( (short) 4 );

            PrimaryChar primaryChar = MultiSimilarConstructorPropertyMapper.INSTANCE.map(
                new MultiSimilarConstructorPropertyMapper.Source(
                    "Kermit",
                    "the Frog"
                ),
                't'
            );
            assertThat( primaryChar ).isNotNull();
            assertThat( primaryChar.getFirstName() ).isEqualTo( "Kermit" );
            assertThat( primaryChar.getLastName() ).isEqualTo( "the Frog" );
            assertThat( primaryChar.getC() ).isEqualTo( 't' );

            PrimaryFloat primaryFloat = MultiSimilarConstructorPropertyMapper.INSTANCE.map(
                new MultiSimilarConstructorPropertyMapper.Source(
                    "Kermit",
                    "the Frog"
                ),
                42.2f
            );
            assertThat( primaryFloat ).isNotNull();
            assertThat( primaryFloat.getFirstName() ).isEqualTo( "Kermit" );
            assertThat( primaryFloat.getLastName() ).isEqualTo( "the Frog" );
            assertThat( primaryFloat.getPrice() ).isEqualTo( 42.2f );

            PrimaryDouble primaryDouble = MultiSimilarConstructorPropertyMapper.INSTANCE.map(
                new MultiSimilarConstructorPropertyMapper.Source(
                    "Kermit",
                    "the Frog"
                ),
                42.2
            );
            assertThat( primaryDouble ).isNotNull();
            assertThat( primaryDouble.getFirstName() ).isEqualTo( "Kermit" );
            assertThat( primaryDouble.getLastName() ).isEqualTo( "the Frog" );
            assertThat( primaryDouble.getPrice() ).isEqualTo( 42.2d );

            PrimaryArray primaryArray = MultiSimilarConstructorPropertyMapper.INSTANCE.map(
                new MultiSimilarConstructorPropertyMapper.Source(
                    "Kermit",
                    "the Frog"
                ),
                new String[] { "Kermit", "the Frog" }
            );
            assertThat( primaryArray ).isNotNull();
            assertThat( primaryArray.getFirstName() ).isEqualTo( "Kermit" );
            assertThat( primaryArray.getLastName() ).isEqualTo( "the Frog" );
            assertThat( primaryArray.getElements() ).containsExactly( "Kermit", "the Frog" );
        }

        @ProcessorTest
        @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                @Diagnostic(
                    kind = javax.tools.Diagnostic.Kind.ERROR,
                    type = MultiSimilarConstructorPropertyMapper.class,
                    line = 19,
                    messageRegExp = "Ambiguous constructors found for creating .*PrimaryString"
                ),
                @Diagnostic(
                    kind = javax.tools.Diagnostic.Kind.ERROR,
                    type = MultiSimilarConstructorPropertyMapper.class,
                    line = 21,
                    messageRegExp = "Ambiguous constructors found for creating .*PrimaryInt"
                ),
                @Diagnostic(
                    kind = javax.tools.Diagnostic.Kind.ERROR,
                    type = MultiSimilarConstructorPropertyMapper.class,
                    line = 23,
                    messageRegExp = "Ambiguous constructors found for creating .*PrimaryLong"
                ),
                @Diagnostic(
                    kind = javax.tools.Diagnostic.Kind.ERROR,
                    type = MultiSimilarConstructorPropertyMapper.class,
                    line = 25,
                    messageRegExp = "Ambiguous constructors found for creating .*PrimaryBoolean"
                ),
                @Diagnostic(
                    kind = javax.tools.Diagnostic.Kind.ERROR,
                    type = MultiSimilarConstructorPropertyMapper.class,
                    line = 27,
                    messageRegExp = "Ambiguous constructors found for creating .*PrimaryByte"
                ),
                @Diagnostic(
                    kind = javax.tools.Diagnostic.Kind.ERROR,
                    type = MultiSimilarConstructorPropertyMapper.class,
                    line = 29,
                    messageRegExp = "Ambiguous constructors found for creating .*PrimaryShort"
                ),
                @Diagnostic(
                    kind = javax.tools.Diagnostic.Kind.ERROR,
                    type = MultiSimilarConstructorPropertyMapper.class,
                    line = 31,
                    messageRegExp = "Ambiguous constructors found for creating .*PrimaryChar"
                ),
                @Diagnostic(
                    kind = javax.tools.Diagnostic.Kind.ERROR,
                    type = MultiSimilarConstructorPropertyMapper.class,
                    line = 33,
                    messageRegExp = "Ambiguous constructors found for creating .*PrimaryFloat"
                ),
                @Diagnostic(
                    kind = javax.tools.Diagnostic.Kind.ERROR,
                    type = MultiSimilarConstructorPropertyMapper.class,
                    line = 35,
                    messageRegExp = "Ambiguous constructors found for creating .*PrimaryDouble"
                ),
                @Diagnostic(
                    kind = javax.tools.Diagnostic.Kind.ERROR,
                    type = MultiSimilarConstructorPropertyMapper.class,
                    line = 37,
                    messageRegExp = "Ambiguous constructors found for creating .*PrimaryArray"
                )


            }
        )
        void shouldFailToCompile() {

        }
    }

    @Nested
    @WithClasses({
        MultiDefaultConstructorPropertyMapper.class,
    })
    @WithKotlinSources({
        "MultiDefaultConstructorProperty.kt",
        "Default.kt"
    })
    class MultiDefaultConstructor {

        @ProcessorTest
        @WithKotlin
        void shouldCompileWithoutWarnings() {

            MultiDefaultConstructorProperty property = MultiDefaultConstructorPropertyMapper.INSTANCE
                .map( new MultiDefaultConstructorPropertyMapper.Source(
                    "Kermit",
                    "the Frog",
                    "Kermit the Frog"
                ) );
            assertThat( property ).isNotNull();
            assertThat( property.getFirstName() ).isEqualTo( "Kermit" );
            assertThat( property.getLastName() ).isEqualTo( "the Frog" );
            assertThat( property.getDisplayName() ).isNull();
        }

        @ProcessorTest
        void shouldCompileWithoutKotlin() {
            MultiDefaultConstructorProperty property = MultiDefaultConstructorPropertyMapper.INSTANCE
                .map( new MultiDefaultConstructorPropertyMapper.Source(
                    "Kermit",
                    "the Frog",
                    "Kermit the Frog"
                ) );
            assertThat( property ).isNotNull();
            assertThat( property.getFirstName() ).isEqualTo( "Kermit" );
            assertThat( property.getLastName() ).isEqualTo( "the Frog" );
            assertThat( property.getDisplayName() ).isNull();
        }
    }

    @Nested
    @WithClasses({
        UnsignedPropertyMapper.class,
    })
    @WithKotlinSources("UnsignedProperty.kt")
    class Unsigned {

        @ProcessorTest
        @WithKotlin
        void shouldCompileWithoutWarnings() {

            UnsignedProperty property = UnsignedPropertyMapper.INSTANCE.map( new UnsignedPropertyMapper.Source( 10 ) );
            assertThat( property ).isNotNull();
            assertThat( property.getAge() ).isEqualTo( 10 );

            UnsignedPropertyMapper.Source source = UnsignedPropertyMapper.INSTANCE.map( new UnsignedProperty( 20 ) );
            assertThat( source ).isNotNull();
            assertThat( source.getAge() ).isEqualTo( 20 );
        }

        @ProcessorTest
        @WithTestDependency( "kotlin-stdlib" )
        @ExpectedCompilationOutcome(
            value = CompilationResult.SUCCEEDED,
            diagnostics = {
                @Diagnostic(
                    kind = javax.tools.Diagnostic.Kind.WARNING,
                    type = UnsignedPropertyMapper.class,
                    line = 19,
                    messageRegExp = "Unmapped target property: \"copy-.*\"\\."
                )
            }
        )
        void shouldCompileWithoutKotlinJvmMetadata() {
            UnsignedProperty property = UnsignedPropertyMapper.INSTANCE.map( new UnsignedPropertyMapper.Source( 10 ) );
            assertThat( property ).isNotNull();
            assertThat( property.getAge() ).isEqualTo( 10 );

            UnsignedPropertyMapper.Source source = UnsignedPropertyMapper.INSTANCE.map( new UnsignedProperty( 20 ) );
            assertThat( source ).isNotNull();
            assertThat( source.getAge() ).isEqualTo( 20 );
        }
    }
}
