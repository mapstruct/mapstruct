/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.records;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mapstruct.itest.records.CustomerDto;
import org.mapstruct.itest.records.CustomerEntity;
import org.mapstruct.itest.records.CustomerMapper;

import static org.assertj.core.api.Assertions.assertThat;

class RecordsTest {

    @Test
    void shouldMapRecord() {
        CustomerEntity customer = CustomerMapper.INSTANCE.fromRecord( new CustomerDto( "Kermit", "kermit@test.com" ) );

        assertThat( customer ).isNotNull();
        assertThat( customer.getName() ).isEqualTo( "Kermit" );
        assertThat( customer.getMail() ).isEqualTo( "kermit@test.com" );
    }

    @Test
    void shouldMapIntoRecord() {
        CustomerEntity entity = new CustomerEntity();
        entity.setName( "Kermit" );
        entity.setMail( "kermit@test.com" );

        CustomerDto customer = CustomerMapper.INSTANCE.toRecord( entity );

        assertThat( customer ).isNotNull();
        assertThat( customer.name() ).isEqualTo( "Kermit" );
        assertThat( customer.email() ).isEqualTo( "kermit@test.com" );
    }

    @Test
    void shouldMapIntoGenericRecord() {
        CustomerEntity entity = new CustomerEntity();
        entity.setName( "Kermit" );
        entity.setMail( "kermit@test.com" );

        GenericRecord<String> value = CustomerMapper.INSTANCE.toValue( entity );

        assertThat( value ).isNotNull();
        assertThat( value.value() ).isEqualTo( "Kermit" );
    }

    @Test
    void shouldMapIntoRecordWithList() {
        Car car = new Car();
        car.setWheelPositions( Arrays.asList( new WheelPosition( "left" ) ) );

        CarDto carDto = CarAndWheelMapper.INSTANCE.carDtoFromCar(car);

        assertThat( carDto ).isNotNull();
        assertThat( carDto.wheelPositions() )
            .containsExactly( "left" );
    }

    @Test
    void shouldMapMemberRecord() {
        MemberEntity member = MemberMapper.INSTANCE.fromRecord( new MemberDto( true, false ) );

        assertThat( member ).isNotNull();
        assertThat( member.getIsActive() ).isTrue();
        assertThat( member.getPremium() ).isFalse();
    }

    @Test
    void shouldMapIntoMemberRecord() {
        MemberEntity entity = new MemberEntity();
        entity.setIsActive( false );
        entity.setPremium( true );

        MemberDto value = MemberMapper.INSTANCE.toRecord( entity );

        assertThat( value ).isNotNull();
        assertThat( value.isActive() ).isFalse();
        assertThat( value.premium() ).isTrue();
    }

    @Test
    void shouldUseDefaultConstructor() {
        Task entity = new Task( "some-id", 1000L );

        TaskDto value = TaskMapper.INSTANCE.toRecord( entity );

        assertThat( value ).isNotNull();
        assertThat( value.id() ).isEqualTo( "some-id" );
        assertThat( value.number() ).isEqualTo( 1L );
    }

}
