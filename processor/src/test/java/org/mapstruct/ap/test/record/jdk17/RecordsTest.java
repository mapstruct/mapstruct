/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.record.jdk17;

import java.util.Arrays;

import org.junit.jupiter.api.Nested;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.Compiler;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
class RecordsTest {

    @Nested
    @WithClasses(SimpleRecordMapper.class)
    class Simple {

        @ProcessorTest(Compiler.JDK)
        void shouldMapRecord() {
            SimpleRecordMapper.CustomerEntity customer = SimpleRecordMapper.INSTANCE
                .fromRecord( new SimpleRecordMapper.CustomerDto(
                    "Kermit",
                    "kermit@test.com"
                ) );

            assertThat( customer ).isNotNull();
            assertThat( customer.getName() ).isEqualTo( "Kermit" );
            assertThat( customer.getMail() ).isEqualTo( "kermit@test.com" );
        }

        @ProcessorTest(Compiler.JDK)
        void shouldMapIntoRecord() {
            SimpleRecordMapper.CustomerEntity entity = new SimpleRecordMapper.CustomerEntity();
            entity.setName( "Kermit" );
            entity.setMail( "kermit@test.com" );

            SimpleRecordMapper.CustomerDto customer = SimpleRecordMapper.INSTANCE.toRecord( entity );

            assertThat( customer ).isNotNull();
            assertThat( customer.name() ).isEqualTo( "Kermit" );
            assertThat( customer.email() ).isEqualTo( "kermit@test.com" );
        }

        @ProcessorTest(Compiler.JDK)
        void shouldMapIntoGenericRecord() {
            SimpleRecordMapper.CustomerEntity entity = new SimpleRecordMapper.CustomerEntity();
            entity.setName( "Kermit" );
            entity.setMail( "kermit@test.com" );

            SimpleRecordMapper.GenericRecord<String> value = SimpleRecordMapper.INSTANCE.toValue( entity );

            assertThat( value ).isNotNull();
            assertThat( value.value() ).isEqualTo( "Kermit" );
        }
    }

    @Nested
    @WithClasses(RecordWithListMapper.class)
    class List {

        @ProcessorTest(Compiler.JDK)
        void shouldMapIntoRecordWithList() {
            RecordWithListMapper.Car car = new RecordWithListMapper.Car();
            car.setWheelPositions( Arrays.asList( new RecordWithListMapper.WheelPosition( "left" ) ) );

            RecordWithListMapper.CarDto carDto = RecordWithListMapper.INSTANCE.carDtoFromCar( car );

            assertThat( carDto ).isNotNull();
            assertThat( carDto.wheelPositions() )
                .containsExactly( "left" );
        }

    }

    @Nested
    @WithClasses(BooleanComponentMapper.class)
    class Boolean {

        @ProcessorTest(Compiler.JDK)
        void shouldMapRecord() {
            BooleanComponentMapper.MemberEntity member = BooleanComponentMapper.INSTANCE
                .fromRecord( new BooleanComponentMapper.MemberDto(
                    true,
                    false
                ) );

            assertThat( member ).isNotNull();
            assertThat( member.getIsActive() ).isTrue();
            assertThat( member.getPremium() ).isFalse();
        }

        @ProcessorTest(Compiler.JDK)
        void shouldMapIntoRecord() {
            BooleanComponentMapper.MemberEntity entity = new BooleanComponentMapper.MemberEntity();
            entity.setIsActive( false );
            entity.setPremium( true );

            BooleanComponentMapper.MemberDto value = BooleanComponentMapper.INSTANCE.toRecord( entity );

            assertThat( value ).isNotNull();
            assertThat( value.isActive() ).isEqualTo( false );
            assertThat( value.premium() ).isEqualTo( true );
        }

    }

    @Nested
    @WithClasses(DefaultConstructorMapper.class)
    class DefaultConstructor {

        @ProcessorTest(Compiler.JDK)
        void shouldUseDefaultConstructor() {
            DefaultConstructorMapper.Task entity = new DefaultConstructorMapper.Task( "some-id", 1000L );

            DefaultConstructorMapper.TaskDto value = DefaultConstructorMapper.INSTANCE.toRecord( entity );

            assertThat( value ).isNotNull();
            assertThat( value.id() ).isEqualTo( "some-id" );
            assertThat( value.number() ).isEqualTo( 1L );
        }

    }

    @Nested
    @WithClasses(NestedRecordMapper.class)
    class NestedRecord {

        @ProcessorTest(Compiler.JDK)
        void shouldMapRecord() {
            NestedRecordMapper.CareProvider source = new NestedRecordMapper.CareProvider(
                "kermit",
                new NestedRecordMapper.Address( "Sesame Street", "New York" )
            );
            NestedRecordMapper.CareProviderDto target = NestedRecordMapper.INSTANCE.map( source );

            assertThat( target ).isNotNull();
            assertThat( target.id() ).isEqualTo( "kermit" );
            assertThat( target.street() ).isEqualTo( "Sesame Street" );
            assertThat( target.city() ).isEqualTo( "New York" );
        }
    }

}
